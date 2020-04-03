package util.applitools.tests.validkey;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.Credentials;
import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractTest;

//@Browser("Chrome_headless")
@Browser("Chrome_1500x1000")
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
        System.out.println(ApplitoolsApi.getConfiguration().matchLevel());
    }
}
