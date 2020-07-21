package util.applitools.tests.validkey.setuptest;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import util.applitools.ApplitoolsApi;
import util.applitools.NeodymiumApplitoolsSetup;

public class NeodymiumApplitoolsGlobalSetupTest extends AbstractNeodymiumApplitoolsSetupTest
{
    @Rule
    public NeodymiumApplitoolsSetup setup = new NeodymiumApplitoolsSetup(true);

    @Test
    public void testGlobalSetup()
    {
        Assert.assertTrue(ApplitoolsApi.getEyes().getConfiguration().getBatch().toString()
                                       .contains("'" + ApplitoolsApi.getConfiguration().batch() + "' - " + format.format(new Date())));
    }
}
