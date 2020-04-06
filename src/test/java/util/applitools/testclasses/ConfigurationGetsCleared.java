package util.applitools.testclasses;

import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractTest;

public class ConfigurationGetsCleared extends AbstractTest
{
    @Before
    public void setupGlobal()
    {
        final String apiKey = UUID.randomUUID().toString();
        properties2.put("applitools.apiKey", apiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        ApplitoolsApi.setupGlobal();
    }

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
