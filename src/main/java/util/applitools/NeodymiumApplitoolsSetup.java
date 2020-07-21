package util.applitools;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class NeodymiumApplitoolsSetup extends TestWatcher
{
    private boolean useGlobalSetup;

    private String batchName;

    public NeodymiumApplitoolsSetup()
    {
        this(false);
    }

    public NeodymiumApplitoolsSetup(boolean useGlobalSetup)
    {
        this.useGlobalSetup = useGlobalSetup;
    }

    public NeodymiumApplitoolsSetup(String batchName)
    {
        this();
        this.batchName = batchName;
    }

    @Override
    protected void starting(Description description)
    {
        if (System.getenv("APIKEY") != null)
        {
            ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", System.getenv("APIKEY"));
        }
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
