package util.applitools.validkay;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import tests.AbstractTest;
import util.Credentials;
import util.applitools.ApplitoolsApi;

@Browser("Chrome_headless")
public abstract class AbstractValidKeyTest extends AbstractTest
{
    protected static final Credentials CREDENTIALS = ConfigFactory.create(Credentials.class, System.getenv());

    protected static final String applitoolsApiKey = CREDENTIALS.applitoolsApiKey();

    protected static final String username = CREDENTIALS.username();

    protected static final String password = CREDENTIALS.password();

    protected static final String batchName = "Plugin Unit Tests";

    @Before
    public void beforeEach()
    {
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", applitoolsApiKey);
        ApplitoolsApi.getConfiguration().setProperty("applitools.projectName", "Unit-Test");
        ApplitoolsApi.setupGroupingOfTestsByName(batchName);
    }
}
