package util.applitools;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import com.xceptance.neodymium.NeodymiumRunner;

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
        ApplitoolsApi.closeEyes();
    }
}
