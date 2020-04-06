package util.applitools.tests;

import java.io.IOException;
import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.applitools.eyes.MatchLevel;

import util.applitools.ApplitoolsConfiguration;
import util.applitools.testclasses.ConfigurationGetsCleared;

public class ApplitoolsConfigurationTest extends AbstractTest
{
    @Test
    public void testProjectName() throws IOException, InterruptedException
    {
        final String projectName = "Test-Project";
        properties2.put("applitools.projectName", projectName);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        Assert.assertEquals(projectName, ConfigFactory.create(ApplitoolsConfiguration.class).projectName());
    }

    @Test
    public void testMatchLevel() throws IOException, InterruptedException
    {
        final String matchLevel = "NONE";
        properties2.put("applitools.matchLevel", matchLevel);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        Assert.assertEquals(MatchLevel.valueOf(matchLevel), ConfigFactory.create(ApplitoolsConfiguration.class).matchLevel());
    }

    @Test
    public void testApiKey() throws IOException, InterruptedException
    {
        final String apiKey = UUID.randomUUID().toString();
        properties2.put("applitools.apiKey", apiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        Assert.assertEquals(apiKey, ConfigFactory.create(ApplitoolsConfiguration.class).applitoolsApiKey());
    }

    @Test
    public void testBatch() throws IOException, InterruptedException
    {
        final String batchName = "Test-Batch";
        properties2.put("applitools.batch", batchName);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        Assert.assertEquals(batchName, ConfigFactory.create(ApplitoolsConfiguration.class).batch());
    }

    @Test
    public void testThrowException() throws IOException, InterruptedException
    {
        final String throwException = "false";
        properties2.put("applitools.throwException", throwException);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty("neodymium.temporaryConfigFile", "file:" + fileLocation);
        Assert.assertEquals(Boolean.parseBoolean(throwException), ConfigFactory.create(ApplitoolsConfiguration.class).throwException());
    }

    @Test
    public void testConfigurationGetCleared()
    {
        // test that NeodymiumRunner clears the context before each run
        Result result = JUnitCore.runClasses(ConfigurationGetsCleared.class);
        checkPass(result, 2, 0, 0);
    }
}
