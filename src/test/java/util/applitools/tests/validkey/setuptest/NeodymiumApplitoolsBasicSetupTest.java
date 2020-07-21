package util.applitools.tests.validkey.setuptest;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import util.applitools.ApplitoolsApi;
import util.applitools.NeodymiumApplitoolsSetup;

public class NeodymiumApplitoolsBasicSetupTest extends AbstractNeodymiumApplitoolsSetupTest
{
    @Rule
    public NeodymiumApplitoolsSetup setup = new NeodymiumApplitoolsSetup();

    @Test
    public void testBasicSetup()
    {
        Assert.assertTrue(ApplitoolsApi.getEyes().getConfiguration().getBatch().toString().contains(format.format(new Date())));
    }
}
