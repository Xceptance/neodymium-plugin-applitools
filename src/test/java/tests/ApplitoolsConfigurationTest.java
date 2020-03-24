package tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xceptance.neodymium.NeodymiumRunner;

import util.applitools.ApplitoolsConfiguration;

@RunWith(NeodymiumRunner.class)
public class ApplitoolsConfigurationTest extends AbstractTest
{
    private static final String filename = "config/dev-applitools.properties";

    @BeforeClass
    public static void createPropertiesFile() throws IOException
    {
        new File(filename).createNewFile();
    }

    @AfterClass
    public static void deletePropertiesFile() throws IOException
    {
        new File(filename).delete();
    }

    @Test
    public void testProjectName()
    {
        final String projectName = "Test-Project";
        writePropertiy("applitools.projectName", projectName);
        Assert.assertEquals(projectName, ConfigFactory.create(ApplitoolsConfiguration.class).projectName());
    }

    @Test
    public void testMatchLevel()
    {
        final String matchLevel = "NONE";
        writePropertiy("applitools.matchLevel", matchLevel);
        Assert.assertEquals(matchLevel, ConfigFactory.create(ApplitoolsConfiguration.class).matchLevel());
    }

    @Test
    public void testApiKey()
    {
        final String apiKey = UUID.randomUUID().toString();
        writePropertiy("applitools.apiKey", apiKey);
        Assert.assertEquals(apiKey, ConfigFactory.create(ApplitoolsConfiguration.class).apiKey());
    }

    @Test
    public void testBatch()
    {
        final String batchName = "Test-Batch";
        writePropertiy("applitools.batch", batchName);
        Assert.assertEquals(batchName, ConfigFactory.create(ApplitoolsConfiguration.class).batch());
    }

    @Test
    public void testThrowException()
    {
        final String throwException = "true";
        writePropertiy("applitools.throwException", throwException);
        Assert.assertEquals(throwException, ConfigFactory.create(ApplitoolsConfiguration.class).throwException());
    }

    private void writePropertiy(String name, String value)
    {
        try
        {
            OutputStream output = new FileOutputStream(filename, true);

            Properties prop = new Properties();
            // set the properties value
            prop.setProperty(name, value);

            // save properties to project root folder
            prop.store(output, null);
        }
        catch (IOException io)
        {
            io.printStackTrace();
        }
    }
}
