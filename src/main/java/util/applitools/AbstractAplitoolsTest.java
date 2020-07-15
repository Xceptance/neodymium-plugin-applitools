package util.applitools;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import com.xceptance.neodymium.NeodymiumRunner;

@RunWith(NeodymiumRunner.class)
public class AbstractAplitoolsTest
{
    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public static void setupBatch()
    {
        ApplitoolsApi.setupGlobal();
    }

    @Before
    public void openEyes()
    {
        ApplitoolsApi.openEyes(name.getMethodName());

        ApplitoolsApi.addProperty("purpose", "home page");
    }

    @After
    public void endAssertions()
    {
        ApplitoolsApi.endAssertions();
    }
}
