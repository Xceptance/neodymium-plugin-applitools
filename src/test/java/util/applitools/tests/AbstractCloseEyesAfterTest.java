package util.applitools.tests;

import org.junit.After;

import util.applitools.ApplitoolsApi;

public abstract class AbstractCloseEyesAfterTest extends AbstractTest
{
    @After
    public void closeEyes()
    {
        try
        {
            ApplitoolsApi.closeEyes();
        }
        catch (RuntimeException e)
        {

        }
    }
}
