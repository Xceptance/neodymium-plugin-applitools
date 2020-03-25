package tests;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.applitools.eyes.EyesException;
import com.codeborne.selenide.Selenide;

import util.applitools.ApplitoolsApi;

public class ApplitoolsApiExceptionsTest extends AbstractTest
{

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSetupGlobalWithoutApiKey() throws IOException
    {
        new File(devPropertiesFilename).createNewFile();
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("No API Key found; Please set applitools.apiKey property in applitools.properties");
        ApplitoolsApi.setupGlobal();
    }

    @Test
    public void testOpenEyesWithInvalidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = randomInvalidApiKey();
        writePropertiy(devPropertiesFilename, "applitools.apiKey", invalidApiKey);

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
        final String invalidApiKey = randomInvalidApiKey();
        writePropertiy(devPropertiesFilename, "applitools.apiKey", invalidApiKey);

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Eyes not open");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.assertPage("Homepage");
    }

    @Test
    public void testElementAssertBeforeEyesOpened() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = randomInvalidApiKey();
        writePropertiy(devPropertiesFilename, "applitools.apiKey", invalidApiKey);

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Eyes not open");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.assertElement("#navigation", "top navigation menu");
    }
}
