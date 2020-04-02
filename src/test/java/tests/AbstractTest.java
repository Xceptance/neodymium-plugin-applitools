package tests;

import org.junit.After;
import org.junit.runner.RunWith;

import com.xceptance.neodymium.NeodymiumRunner;

import util.applitools.ApplitoolsApi;

@RunWith(NeodymiumRunner.class)
public abstract class AbstractTest
{
    @After
    public void cleanup()
    {
        ApplitoolsApi.cleanConfigurations();
        try
        {
            ApplitoolsApi.endAssertions();
        }
        catch (RuntimeException e)
        {

        }
    }
}
