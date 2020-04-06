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
     * Retrieves the context instance for the current Thread.
     * 
     * @return the context instance for the current Thread
     */
    public static ApplitoolsConfiguration getConfiguration()
    {
        return CONFIGURATION.computeIfAbsent(Thread.currentThread(), key -> {
            return ConfigFactory.create(ApplitoolsConfiguration.class);
        });
    }

    public static Eyes getEyes()
    {
        return eyes.get();
    }

    public static void setupGlobal()
    {
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

    public static void setupBasic()
    {
        getEyes().setMatchLevel(getConfiguration().matchLevel());
        getEyes().setApiKey(getApplitoolsApiKey());
    }

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
