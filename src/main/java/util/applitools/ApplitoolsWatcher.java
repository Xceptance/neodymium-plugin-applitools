package util.applitools;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

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
        ApplitoolsApi.closeEyes();
    }
}
