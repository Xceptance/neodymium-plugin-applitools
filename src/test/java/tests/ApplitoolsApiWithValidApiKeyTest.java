package tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;

public class ApplitoolsApiWithValidApiKeyTest extends AbstractTest
{ // for local test run, please enter your api key here
    private static final String apiKey = System.getenv("API_KEY");

    @After
    public void endAssertions()
    {
        ApplitoolsApi.endAssertions();
    }

    @Browser("Chrome_1024x768")
    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        writePropertiy(devPropertiesFilename, "applitools.apiKey", apiKey);
        writePropertiy(devPropertiesFilename, "applitools.projectName", "Unit-Test");
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.openEyes("test");
    }
}
