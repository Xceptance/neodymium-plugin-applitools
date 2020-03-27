package tests;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;

@Ignore
@Browser("Chrome_headless")
public class ApplitoolsApiWithValidApiKeyTest extends AbstractTest
{
    // for local test run, please enter your api key here
    private static final String apiKey = System.getenv("API_KEY");

    private static final String batchName = "Plugin Unit Tests";

    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", apiKey);
        ApplitoolsApi.getConfiguration().setProperty("applitools.projectName", "Unit-Test");

        ApplitoolsApi.setupForGroupOfTests(batchName);
        ApplitoolsApi.openEyes("test");
    }

    @Test
    public void testAssertPage() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", apiKey);
        ApplitoolsApi.getConfiguration().setProperty("applitools.projectName", "Unit-Test");

        ApplitoolsApi.setupForGroupOfTests(batchName);
        ApplitoolsApi.openEyes("test");
    }
}
