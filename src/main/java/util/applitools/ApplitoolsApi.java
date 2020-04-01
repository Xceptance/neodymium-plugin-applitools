package util.applitools;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
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
    private static ThreadLocal<ApplitoolsConfiguration> applitoolsConfiguration = ThreadLocal.withInitial(new Supplier<ApplitoolsConfiguration>()
    {
        @Override
        public ApplitoolsConfiguration get()
        {
            return ConfigFactory.create(ApplitoolsConfiguration.class);
        }
    });

    private static ThreadLocal<Eyes> eyes = ThreadLocal.withInitial(new Supplier<Eyes>()
    {
        @Override
        public Eyes get()
        {
            return new Eyes();
        }
    });

    protected static HashMap<String, BatchInfo> batches = new HashMap<String, BatchInfo>();

    public static ApplitoolsConfiguration getConfiguration()
    {
        return applitoolsConfiguration.get();
    }

    public static Eyes getEyes()
    {
        return eyes.get();
    }

    public static void setupGlobal()
    {
        setupGroupingOfTestsByName(applitoolsConfiguration.get().batch());
    }

    public static void setupGroupingOfTestsByName(String batchNameForGroup)
    {
        BatchInfo batch;
        if (batches.containsKey(batchNameForGroup))
        {
            batch = batches.get(batchNameForGroup);
        }
        else
        {
            batch = new BatchInfo(batchNameForGroup);
            String batchId = BatchHelper.getBatch(batchNameForGroup);
            if (batchId == null)
            {
                String newBatchId = BatchHelper.addBatch(batchNameForGroup);
                if (newBatchId != null)
                {
                    batch.setId(newBatchId);
                }
            }
            else
            {
                batch.setId(batchId);
            }
            batches.put(batchNameForGroup, batch);
        }
        getEyes().setBatch(batch);
        setupForSingleTest();
    }

    public static void setupForSingleTest()
    {
        setMatchLevel(applitoolsConfiguration.get().matchLevel());

        getEyes().setApiKey(getApiKey());
    }

    public static void addProperty(String name, String value)
    {
        getEyes().addProperty(name, value);
    }

    public static void setMatchLevel(String matchLevel)
    {
        getEyes().setMatchLevel(parseMatchLevel(matchLevel));
    }

    public static void openEyes(String testName)
    {
        getEyes().open(getDriver(), applitoolsConfiguration.get().projectName(), testName);
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

    public static void assertPage(String pageName)
    {
        getEyes().checkWindow(pageName);
    }

    public static void assertElement(String elementSelector)
    {
        assertElement(elementSelector, elementSelector);
    }

    public static void assertElements(String elementSelector)
    {
        WebDriver driver = getDriver();
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

    public static void assertElement(String elementSelector, String tag)
    {
        if (elementSelector.substring(0, 1).equals("//"))
        {
            getEyes().checkElement(By.xpath(elementSelector), tag);

        }
        else
        {
            getEyes().checkElement(By.cssSelector(elementSelector), tag);

        }
    }

    public static void endAssertions()
    {
        TestResults allTestResults = getEyes().close(Boolean.parseBoolean(applitoolsConfiguration.get().throwException()));
        if (allTestResults == null)
        {
            throw new RuntimeException("something went wrong, maybe you have not called Applitools.openEyes() before calling this method");
        }
        AllureAddons.addToReport("number of missmatches", allTestResults.getMismatches());
        AllureAddons.addToReport("link to results of visual assetions in this test", allTestResults.getUrl());
        getEyes().abortIfNotClosed();
    }

    private static String getApiKey()
    {
        String apiKey = applitoolsConfiguration.get().apiKey();
        if (isNullOrEmpty(apiKey))
        {
            throw new RuntimeException("No API Key found; Please set applitools.apiKey property in applitools.properties");
        }
        return apiKey;
    }

    private static RemoteWebDriver getDriver()
    {
        EventFiringWebDriver eventFiringWebDriver = (EventFiringWebDriver) Neodymium.getDriver();
        return (RemoteWebDriver) eventFiringWebDriver.getWrappedDriver();
    }

    private static MatchLevel parseMatchLevel(String matchLevel)
    {
        switch (matchLevel)
        {
            case "LAYOUT2":
                return MatchLevel.LAYOUT2;
            case "LAYOUT":
                return MatchLevel.LAYOUT;
            case "CONTENT":
                return MatchLevel.CONTENT;
            case "EXACT":
                return MatchLevel.EXACT;
            case "NONE":
                return MatchLevel.NONE;
            default:
                return MatchLevel.STRICT;
        }
    }
}
