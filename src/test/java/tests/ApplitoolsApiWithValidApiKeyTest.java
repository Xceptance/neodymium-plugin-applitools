package tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;

@Browser("Chrome_headless")
public class ApplitoolsApiWithValidApiKeyTest extends AbstractTest
{
    @After
    public void endAssertions()
    {
        ApplitoolsApi.endAssertions();
    }

    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        writePropertiy(devPropertiesFilename, "applitools.apiKey", System.getenv("API_KEY"));
        writePropertiy(devPropertiesFilename, "applitools.projectName", "Unit-Test");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.openEyes("test");
    }
}
