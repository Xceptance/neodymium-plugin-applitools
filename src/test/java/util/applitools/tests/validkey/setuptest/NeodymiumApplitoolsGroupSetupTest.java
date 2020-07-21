package util.applitools.tests.validkey.setuptest;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import util.applitools.ApplitoolsApi;
import util.applitools.NeodymiumApplitoolsSetup;

public class NeodymiumApplitoolsGroupSetupTest extends AbstractNeodymiumApplitoolsSetupTest
{
    private String groupName = "The Group";

    @Rule
    public NeodymiumApplitoolsSetup setup = new NeodymiumApplitoolsSetup(groupName);

    @Test
    public void testGroupSetup()
    {
        Assert.assertTrue(ApplitoolsApi.getEyes().getConfiguration().getBatch().toString().contains("'" + groupName + "' - " + format.format(new Date())));
    }
}
