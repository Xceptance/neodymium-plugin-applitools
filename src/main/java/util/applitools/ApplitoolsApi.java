package util.applitools;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.xceptance.neodymium.util.AllureAddons;
import com.xceptance.neodymium.util.Neodymium;

public class ApplitoolsApi
{
    private static final Map<Thread, ApplitoolsConfiguration> CONFIGURATION = Collections.synchronizedMap(new WeakHashMap<>());

    public final static String TEMPORARY_CONFIG_FILE_PROPERTY_NAME = "applitools.temporaryConfigFile";

    private static ThreadLocal<Eyes> eyes = ThreadLocal.withInitial(new Supplier<Eyes>()
    {
        @Override
        public Eyes get()
        {
            return new Eyes();
        }
    });

    protected static HashMap<String, BatchInfo> batches = new HashMap<String, BatchInfo>();

    /**
     * Retrieves the instance of applitools configuration for the current thread.
     * 
     * @return the configuration instance for the current thread
     */
    public static ApplitoolsConfiguration getConfiguration()
    {
        // the property needs to be a valid URI in order to satisfy the Owner framework
        if (null == ConfigFactory.getProperty(TEMPORARY_CONFIG_FILE_PROPERTY_NAME))
        {
            ConfigFactory.setProperty(TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:this/path/should/never/exist/noOneShouldCreateMe.properties");
        }
        return CONFIGURATION.computeIfAbsent(Thread.currentThread(), key -> {
            return ConfigFactory.create(ApplitoolsConfiguration.class);
        });
    }

    /**
     * Method to access eyes instance for current thread. This can be used to make custom manipulations with this
     * object, e.g. to download pictures after test
     * 
     * @return eyes instance for current thread
     */
    public static Eyes getEyes()
    {
        return eyes.get();
    }

    /**
     * Configures eyes object to start test. Configurations will be cleared and read from configuration file again. Use
     * this method to setup tests, which belong to global batch (one with the name you entered in applitools.properties)
     */
    public static void setupGlobal()
    {
        CONFIGURATION.remove(Thread.currentThread());
        getConfiguration();
        final String batch = getConfiguration().batch();
        if (isNullOrEmpty(batch))
        {
            setupBasic();
        }
        else
        {
            setupGroupingOfTestsByName(batch);
        }
    }

    /**
     * Configures eyes object to start test. Configurations will be cleared and read from configuration file again. Use
     * this method with the same batchName parameter to setup the group tests, which belong to one batch , e.g. for all
     * order test call this method with batchName 'order'
     * 
     * @param batchName
     *            name of batch for group of tests
     */
    public synchronized static void setupGroupingOfTestsByName(String batchName)
    {
        CONFIGURATION.remove(Thread.currentThread());
        getConfiguration();
        BatchInfo batch;
        if (!isNullOrEmpty(batchName))
        {
            if (batches.containsKey(batchName))
            {
                batch = batches.get(batchName);
            }
            else
            {
                batch = new BatchInfo(batchName);
                batches.put(batchName, batch);
            }
            getEyes().setBatch(batch);
        }
        setupBasic();
    }

    /**
     * Configures eyes object with minimal configurations. Use this method if you don't want test to belong to any
     * existing batch
     */
    public static void setupBasic()
    {
        CONFIGURATION.remove(Thread.currentThread());
        getConfiguration();
        getEyes().setMatchLevel(getConfiguration().matchLevel());
        getEyes().setApiKey(getApplitoolsApiKey());
    }

    /**
     * Wrapper method for eyes.setMatchLevel. Through this method match level can be changed at any point of the test.
     * To get to know more about avaliable match levels, please read
     * <a href="https://help.applitools.com/hc/en-us/articles/360007188591-Match-Levels">this</a> article
     * 
     * @param matchLevel
     *            string value of com.applitools.eyes.MatchLevel enum object
     */
    public static void setMatchLevel(String matchLevel)
    {
        getEyes().setMatchLevel(MatchLevel.valueOf(matchLevel));
    }

    public static void addProperty(String name, String value)
    {
        getEyes().addProperty(name, value);
    }

    public static void openEyes(String testName)
    {
        getEyes().open(getRemoteWebDriver(), getConfiguration().projectName(), testName);
    }

    /**
     * Use this method to set if Eyes should hide the cursor before the screenshot is captured.
     * 
     * @param hideCaret
     */
    public static void setHideCaret(boolean hideCaret)
    {
        getEyes().setHideCaret(hideCaret);
    }

    public static void assertPage(String pageDescription)
    {
        getEyes().checkWindow(pageDescription);
    }

    public static void assertElements(String elementSelector)
    {
        WebDriver driver = getRemoteWebDriver();
        if (elementSelector.substring(0, 1).equals("//"))
        {
            driver.findElements(By.xpath(elementSelector)).forEach(element -> getEyes().checkElement(element, elementSelector));
        }
        else
        {
            driver.findElements(By.cssSelector(elementSelector)).forEach(element -> getEyes().checkElement(element, elementSelector));
        }
    }

    public static void setWaitBeforeScreenshot(int waitBeforeScreenshots)
    {
        getEyes().setWaitBeforeScreenshots(waitBeforeScreenshots);
    }

    public static void assertElement(By condition, String imageDescription)
    {
        getEyes().checkElement(condition, imageDescription);
    }

    public static void endAssertions()
    {
        TestResults allTestResults = getEyes().close(getConfiguration().throwException());
        if (allTestResults == null)
        {
            throw new RuntimeException("something went wrong, maybe you have not called Applitools.openEyes() before calling this method");
        }
        AllureAddons.addToReport("number of missmatches", allTestResults.getMismatches());
        AllureAddons.addToReport("link to results of visual assetions in this test", allTestResults.getUrl());
        getEyes().abortIfNotClosed();
    }

    private static String getApplitoolsApiKey()
    {
        String applitoolsApiKey = getConfiguration().applitoolsApiKey();
        if (isNullOrEmpty(applitoolsApiKey))
        {
            throw new RuntimeException("No Applitools API Key found: Please set the 'applitools.apiKey' property in 'config/applitools.properties' file.");
        }
        return applitoolsApiKey;
    }

    private static RemoteWebDriver getRemoteWebDriver()
    {
        EventFiringWebDriver eventFiringWebDriver = (EventFiringWebDriver) Neodymium.getDriver();
        return (RemoteWebDriver) eventFiringWebDriver.getWrappedDriver();
    }
}
