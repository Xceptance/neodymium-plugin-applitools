package tests;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import util.applitools.ApplitoolsConfiguration;

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
    public void testProjectName() throws IOException
    {
        final String projectName = "Test-Project";
        writePropertiy(filename, "applitools.projectName", projectName);
        Assert.assertEquals(projectName, ConfigFactory.create(ApplitoolsConfiguration.class).projectName());
    }

    @Test
    public void testMatchLevel() throws IOException
    {
        final String matchLevel = "NONE";
        writePropertiy(filename, "applitools.matchLevel", matchLevel);
        Assert.assertEquals(matchLevel, ConfigFactory.create(ApplitoolsConfiguration.class).matchLevel());
    }

    @Test
    public void testApiKey() throws IOException
    {
        final String apiKey = UUID.randomUUID().toString();
        writePropertiy(filename, "applitools.apiKey", apiKey);
        Assert.assertEquals(apiKey, ConfigFactory.create(ApplitoolsConfiguration.class).apiKey());
    }

    @Test
    public void testBatch() throws IOException
    {
        final String batchName = "Test-Batch";
        writePropertiy(filename, "applitools.batch", batchName);
        Assert.assertEquals(batchName, ConfigFactory.create(ApplitoolsConfiguration.class).batch());
    }

    @Test
    public void testThrowException() throws IOException
    {
        final String throwException = "true";
        writePropertiy(filename, "applitools.throwException", throwException);
        Assert.assertEquals(throwException, ConfigFactory.create(ApplitoolsConfiguration.class).throwException());
    }

}
