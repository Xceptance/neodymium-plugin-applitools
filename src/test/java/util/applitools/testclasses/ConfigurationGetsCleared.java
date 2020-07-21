package util.applitools.testclasses;

import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.applitools.ApplitoolsApi;
import util.applitools.ApplitoolsConfiguration;
import util.applitools.tests.AbstractCloseEyesAfterTest;

public class ConfigurationGetsCleared extends AbstractCloseEyesAfterTest
{
    @Before
    public void setupGlobal()
    {
        final String apiKey = UUID.randomUUID().toString();
        configProperties.put("applitools.apiKey", apiKey);
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);
        ApplitoolsApi.setupGlobal();
    }

    private static final String propertyName = "test.property";

    @Test
    public void test()
    {
        Assert.assertNull(ApplitoolsApi.getConfiguration().setProperty(propertyName, ""));
        ApplitoolsConfiguration configuration = ApplitoolsApi.getConfiguration();
        configuration.applitoolsApiKey();
    }

    @Test
    public void test321()
    {
        Assert.assertNull(ApplitoolsApi.getConfiguration().setProperty(propertyName, "val1"));
        ApplitoolsConfiguration configuration = ApplitoolsApi.getConfiguration();
        configuration.applitoolsApiKey();
    }
}
