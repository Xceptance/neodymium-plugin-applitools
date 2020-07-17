package util.applitools;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import com.applitools.eyes.TestResults;
import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.util.AllureAddons;

@RunWith(NeodymiumRunner.class)
public abstract class AbstractApplitoolsTest
{
    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public static void beforeClass()
    {
        ApplitoolsApi.setupBasic();
    }

    @Before
    public void before()
    {
        ApplitoolsApi.openEyes(name.getMethodName());
    }

    @After
    public void after()
    {
        TestResults allTestResults = ApplitoolsApi.getEyes().close(ApplitoolsApi.getConfiguration().throwException());
        if (allTestResults == null)
        {
            throw new RuntimeException("something went wrong, maybe you have not called Applitools.openEyes() before calling this method");
        }
        AllureAddons.addToReport("number of missmatches", allTestResults.getMismatches());
        AllureAddons.addToReport("link to results of visual assetions in this test", allTestResults.getUrl());
        ApplitoolsApi.getEyes().closeAsync();
    }
}
