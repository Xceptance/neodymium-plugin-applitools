package util.applitools.tests;

import java.io.IOException;
import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;

import com.applitools.eyes.EyesException;
import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;

@Browser("Chrome_headless")
public class ApplitoolsApiExceptionsTest extends AbstractTest
{

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSetupGlobalWithoutApiKey() throws IOException
    {
        properties2.put("applitools.apiKey", "");
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("No Applitools API Key found: Please set the 'applitools.apiKey' property in 'config/applitools.properties' file.");
        ApplitoolsApi.setupGlobal();
    }

    @Test
    public void testOpenEyesWithInvalidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");

        properties2.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);

        exceptionRule.expect(EyesException.class);
        exceptionRule.expectMessage("eyes.openBase() failed");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.openEyes("test");
    }

    @Test
    public void testEndAssertionBeforeStart()
    {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("something went wrong, maybe you have not called Applitools.openEyes() before calling this method");
        ApplitoolsApi.endAssertions();
    }

    @Test
    public void testPageAssertBeforeEyesOpened() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");

        properties2.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Eyes not open");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.assertPage("Homepage");
    }

    @Test
    public void testElementAssertBeforeEyesOpened() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");

        properties2.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Eyes not open");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
    }
}
