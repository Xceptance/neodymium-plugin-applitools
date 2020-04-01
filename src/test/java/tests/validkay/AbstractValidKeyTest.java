package tests.validkay;

import org.junit.After;
import org.junit.Before;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import tests.AbstractTest;
import util.Credentials;
import util.applitools.ApplitoolsApi;

@Browser("Chrome_headless")
public abstract class AbstractValidKeyTest extends AbstractTest
{
    protected static final Credentials CREDENTIALS = new Credentials();

    protected static final String applitoolsApiKey = CREDENTIALS.getApplitoolsApiKey();

    protected static final String username = CREDENTIALS.getUsername();

    protected static final String password = CREDENTIALS.getPassword();

    protected static final String batchName = "Plugin Unit Tests";

    @Before
    public void before()
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", applitoolsApiKey);
        ApplitoolsApi.getConfiguration().setProperty("applitools.projectName", "Unit-Test");
    }

    @After
    public void endAssertions()
    {
        try
        {
            ApplitoolsApi.endAssertions();
        }
        catch (RuntimeException e)
        {

        }
    }
}
