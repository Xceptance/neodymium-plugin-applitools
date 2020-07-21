package util.applitools.tests.validkey;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.Credentials;
import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractCloseEyesAfterTest;

@Browser("Chrome_headless")
public abstract class AbstractValidKeyTest extends AbstractCloseEyesAfterTest
{
    protected static final Credentials CREDENTIALS = ConfigFactory.create(Credentials.class, System.getenv());

    protected static final String applitoolsApiKey = CREDENTIALS.applitoolsApiKey();

    protected static final String username = CREDENTIALS.username();

    protected static final String password = CREDENTIALS.password();

    protected static final String batchName = "Plugin Unit Tests";

    @Before
    public void beforeEach()
    {
        configProperties.put("applitools.apiKey", applitoolsApiKey);
        configProperties.put("applitools.projectName", "Unit-Test");
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);
        ApplitoolsApi.updateConfiguration();
        ApplitoolsApi.setupGroupingOfTestsByName(batchName);
    }
}
