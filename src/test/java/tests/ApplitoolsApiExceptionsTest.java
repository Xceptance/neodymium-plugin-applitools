package tests;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;

import com.applitools.eyes.EyesException;
import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.DataUtils;

import util.applitools.ApplitoolsApi;

@Browser("Chrome_headless")
public class ApplitoolsApiExceptionsTest extends AbstractTest
{

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSetupGlobalWithoutApiKey() throws IOException
    {
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", "");
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("No API Key found; Please set applitools.apiKey property in applitools.properties");
        ApplitoolsApi.setupGlobal();
    }

    @Test
    public void testOpenEyesWithInvalidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = DataUtils.randomPassword();
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", invalidApiKey);

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
        final String invalidApiKey = DataUtils.randomPassword();
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", invalidApiKey);

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Eyes not open");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.assertPage("Homepage");
    }

    @Test
    public void testElementAssertBeforeEyesOpened() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String invalidApiKey = DataUtils.randomPassword();
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", invalidApiKey);

        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Eyes not open");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
    }
}
