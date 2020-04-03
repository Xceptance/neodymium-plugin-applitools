package util.applitools.testclasses;

import org.junit.Assert;
import org.junit.Test;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractTest;

public class ConfigurationGetsCleared extends AbstractTest
{
    private static final String propertyName = "test.property";

    @Test
    public void test1()
    {
        ApplitoolsApi.getConfiguration().setProperty(propertyName, "val1");
    }

    @Test
    public void test2()
    {
        Assert.assertNull(ApplitoolsApi.getConfiguration().setProperty(propertyName, ""));
    }
}
