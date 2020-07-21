package util.applitools.tests;

import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.applitools.eyes.EyesException;
import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;

@Browser("Chrome_headless")
public class ApplitoolsApiExceptionsTest extends AbstractCloseEyesAfterTest
{
    @Test
    public void testSetupGlobalWithoutApiKey()
    {
        configProperties.put("applitools.apiKey", "");
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);
        ApplitoolsApi.updateConfiguration();
        Assert.assertThrows("No Applitools API Key found: Please set the 'applitools.apiKey' property in 'config/applitools.properties' file.",
                            RuntimeException.class, () -> {
                                ApplitoolsApi.setupGlobal();
                            });
    }

    @Test
    public void testOpenEyesWithInvalidApiKey()
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");

        configProperties.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);

        ApplitoolsApi.updateConfiguration();

        ApplitoolsApi.setupGlobal();
        Assert.assertThrows("eyes.openBase() failed", EyesException.class, () -> {
            ApplitoolsApi.openEyes("test");
        });
    }

    @Test
    public void testEndAssertionBeforeStart()
    {
        Assert.assertThrows("something went wrong, maybe you have not called Applitools.openEyes() before calling this method",
                            RuntimeException.class, () -> {
                                ApplitoolsApi.closeEyes();
                            });
    }

    @Test
    public void testPageAssertBeforeEyesOpened()
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");

        configProperties.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);

        ApplitoolsApi.updateConfiguration();

        ApplitoolsApi.setupGlobal();
        Assert.assertThrows("Eyes not open", IllegalStateException.class, () -> {
            ApplitoolsApi.assertPage("Homepage");
        });
    }

    @Test
    public void testElementAssertBeforeEyesOpened()
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");

        configProperties.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);

        ApplitoolsApi.updateConfiguration();

        ApplitoolsApi.setupGlobal();
        Assert.assertThrows("Eyes not open", IllegalStateException.class, () -> {
            ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
        });
    }
}
