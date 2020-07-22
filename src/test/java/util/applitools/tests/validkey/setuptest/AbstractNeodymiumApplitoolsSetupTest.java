package util.applitools.tests.validkey.setuptest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractTest;

@Browser("Chrome_headless")
public abstract class AbstractNeodymiumApplitoolsSetupTest extends AbstractTest
{
    protected static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:")
    {
        private static final long serialVersionUID = 5383324711820050508L;

        {
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    };

    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public static void apiKeySetup()
    {
        if (System.getenv("APIKEY") != null)
        {
            HashMap<String, String> apiKey = new HashMap<String, String>();
            apiKey.put("applitools.apiKey", System.getenv("APIKEY"));

            File tempConfigFile = new File("./" + configFileLocation);
            writeMapToPropertiesFile(apiKey, tempConfigFile);
            ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + configFileLocation);
            ApplitoolsApi.updateConfiguration();
        }
    }

    @Test
    public void testOpenedEyes()
    {
        Assert.assertEquals(ApplitoolsApi.getEyes().getConfiguration().getTestName(), name.getMethodName());
    }
}
