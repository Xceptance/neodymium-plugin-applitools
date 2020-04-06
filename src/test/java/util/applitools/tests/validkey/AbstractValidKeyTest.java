package util.applitools.tests.validkey;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.Credentials;
import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractTest;

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
        properties2.put("applitools.apiKey", applitoolsApiKey);
        properties2.put("applitools.projectName", "Unit-Test");
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        ApplitoolsApi.setupGroupingOfTestsByName(batchName);
        System.out.println(ApplitoolsApi.getConfiguration().matchLevel());
    }
}
