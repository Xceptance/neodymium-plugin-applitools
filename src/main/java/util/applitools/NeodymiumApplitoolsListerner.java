package util.applitools;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

import com.applitools.eyes.TestResults;
import com.xceptance.neodymium.util.AllureAddons;

public class NeodymiumApplitoolsListerner extends RunListener
{
    // like BeforeClass (not called while run in eclipse)
    // @Override
    // public void testRunStarted(Description description)

    // like Before (will be called before each test but also works in eclipse)
    // to compensate it, the global setup is used here, to make the results of all test methods to be saved in single
    // batch
    @Override
    public void testStarted(Description description)
    {
        System.out.println("before");
        ApplitoolsApi.setupGlobal();
    }

    // like After
    @Override
    public void testFinished(Description description)
    {
        System.out.println("after");
        TestResults allTestResults = ApplitoolsApi.getEyes().close(ApplitoolsApi.getConfiguration().throwException());
        if (allTestResults != null)
        {
            AllureAddons.addToReport("number of missmatches", allTestResults.getMismatches());
            AllureAddons.addToReport("link to results of visual assetions in this test", allTestResults.getUrl());
            ApplitoolsApi.getEyes().abortIfNotClosed();
            // throw new RuntimeException("something went wrong, maybe you have not called Applitools.openEyes() before
            // calling this method");
        }
        // AllureAddons.addToReport("number of missmatches", allTestResults.getMismatches());
        // AllureAddons.addToReport("link to results of visual assetions in this test", allTestResults.getUrl());
        // ApplitoolsApi.getEyes().abortIfNotClosed();
    }
}
