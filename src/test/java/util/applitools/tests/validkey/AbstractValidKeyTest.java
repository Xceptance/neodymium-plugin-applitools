package util.applitools.tests.validkey;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractCloseEyesAfterTest;

@Browser("Chrome_headless")
public abstract class AbstractValidKeyTest extends AbstractCloseEyesAfterTest
{
    protected static final String username = System.getenv("USERNAME") == null ? "<PLACE USERNAME HERE>" : System.getenv("USERNAME");

    protected static final String password = System.getenv("PASSWORD") == null ? "<PLACE PASSWORD HERE>" : System.getenv("PASSWORD");

    protected static final String batchName = "Plugin Unit Tests";

    @Before
    public void beforeEach()
    {
        configProperties.put("applitools.projectName", "Unit-Test");
        writeMapToPropertiesFile(configProperties, tempConfigFile);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);
        ApplitoolsApi.updateConfiguration();
        ApplitoolsApi.setupGroupingOfTestsByName(batchName);
    }
}
