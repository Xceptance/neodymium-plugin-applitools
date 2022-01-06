package util.applitools;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Allure;

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
        getEyes().setHideCaret(getConfiguration().hideCaret());
        getEyes().setWaitBeforeScreenshots(getConfiguration().waitBeforeScreenshot());
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

    /**
     * Method to add property for test. Adds a custom key name/value property that will be associated with tests. You
     * can view these properties and filter and group by these properties in the Test Manager.
     * 
     * @param name
     *            property name
     * @param value
     *            property value
     */
    public static void addProperty(String name, String value)
    {
        getEyes().addProperty(name, value);
    }

    /**
     * Method to start visual assertions. Call this method to start a test, before calling any of the check methods.
     * 
     * @param testName
     *            The name of the test. This name must be unique within the scope of the application name. It may be any
     *            string.
     */
    public static void openEyes(String testName)
    {
        ExecutorService es = Executors.newSingleThreadExecutor();
        TimeLimiter tl = SimpleTimeLimiter.create(es);
        RemoteWebDriver driver = Neodymium.getRemoteWebDriver();
        Eyes eyes = getEyes();
        try
        {
            tl.callWithTimeout(() -> {
                return eyes.open(driver, getConfiguration().projectName(), testName);
            },
                               30, TimeUnit.SECONDS);
        }
        catch (TimeoutException | InterruptedException | ExecutionException e)
        {
            throw new RuntimeException("Opening Applitools eyes took too long. Please make sure you have entered the correct api key", e);
        }
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

    /**
     * Use this method to set the amount of time in milliseconds that Eyes will wait before capturing a screenshot.
     * 
     * @param waitBeforeScreenshots
     *            time in milliseconds
     */
    public static void setWaitBeforeScreenshot(int waitBeforeScreenshots)
    {
        getEyes().setWaitBeforeScreenshots(waitBeforeScreenshots);
    }

    /**
     * Method to make visual assert for the whole page
     * 
     * @param pageDescription
     *            name for the screenshot
     */
    public static void assertPage(String pageDescription)
    {
        getEyes().checkWindow(pageDescription);
    }

    /**
     * Method to make visual assert for elements in collection
     * 
     * @param condition
     *            org.openqa.selenium.By object to select target elements
     * @param description
     *            screenshot description
     */
    public static void assertElements(By condition, String description)
    {
        WebDriver driver = Neodymium.getRemoteWebDriver();
        driver.findElements(condition).forEach((element) -> {
            getEyes().checkElement(element, description);
        });
    }

    /**
     * Method to make visual assert for single element
     * 
     * @param condition
     *            org.openqa.selenium.By object to select target element
     * @param imageDescription
     *            screenshot description
     */
    public static void assertElement(By condition, String imageDescription)
    {
        getEyes().checkElement(condition, imageDescription);
    }

    /**
     * This method should be called at the end of each test to end all visual assertions and to add link to results to
     * your allure report
     */
    public static void closeEyes()
    {
        TestResults allTestResults = getEyes().close(getConfiguration().throwException());
        if (allTestResults.getUrl() == null)
        {
            throw new RuntimeException("Something went wrong, maybe you have not called Applitools.openEyes() before calling this method");
        }
        Allure.link("Number of missmatches found by Applitools is " + allTestResults.getMismatches()
                    + ". Please, see more details about visual compare results by this link", allTestResults.getUrl());
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
}
