package util.applitools;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.applitools.eyes.TestResults;
import com.xceptance.neodymium.util.AllureAddons;

public class ApplitoolsWatcher extends TestWatcher
{
    private boolean useGlobalSetup;

    private String batchName;

    public ApplitoolsWatcher(boolean useGlobalSetup)
    {
        this.useGlobalSetup = useGlobalSetup;
    }

    public ApplitoolsWatcher()
    {
        this.useGlobalSetup = false;
    }

    public ApplitoolsWatcher(String batchName)
    {
        this.useGlobalSetup = false;
        this.batchName = batchName;
    }

    @Override
    protected void starting(Description description)
    {
        if (useGlobalSetup)
        {
            ApplitoolsApi.setupGlobal();
        }
        else if (batchName != null)
        {
            ApplitoolsApi.setupGroupingOfTestsByName(batchName);
        }
        else
        {
            ApplitoolsApi.setupBasic();
        }
        ApplitoolsApi.openEyes(description.getMethodName());
    }

    @Override
    protected void finished(Description description)
    {
        TestResults allTestResults = ApplitoolsApi.getEyes().close(ApplitoolsApi.getConfiguration().throwException());
        if (allTestResults == null)
        {
            throw new RuntimeException("something went wrong, maybe you have not called Applitools.openEyes() before calling this method");
        }
        AllureAddons.addToReport("number of missmatches", allTestResults.getMismatches());
        AllureAddons.addToReport("link to results of visual assetions in this test", allTestResults.getUrl());
        ApplitoolsApi.getEyes().abortIfNotClosed();
    }
}
