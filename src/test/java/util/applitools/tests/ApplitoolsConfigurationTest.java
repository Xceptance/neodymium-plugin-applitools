package util.applitools.tests;

import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.applitools.eyes.MatchLevel;

import util.applitools.ApplitoolsApi;
import util.applitools.ApplitoolsConfiguration;
import util.applitools.testclasses.ConfigurationGetsCleared;

public class ApplitoolsConfigurationTest extends AbstractTest
{
    @Test
    public void testProjectName()
    {
        final String projectName = "Test-Project";
        properties2.put("applitools.projectName", projectName);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(projectName, ConfigFactory.create(ApplitoolsConfiguration.class).projectName());
    }

    @Test
    public void testMatchLevel()
    {
        final String matchLevel = "NONE";
        properties2.put("applitools.matchLevel", matchLevel);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(MatchLevel.valueOf(matchLevel), ConfigFactory.create(ApplitoolsConfiguration.class).matchLevel());
    }

    @Test
    public void testApiKey()
    {
        final String apiKey = UUID.randomUUID().toString();
        properties2.put("applitools.apiKey", apiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(apiKey, ConfigFactory.create(ApplitoolsConfiguration.class).applitoolsApiKey());
    }

    @Test
    public void testBatch()
    {
        final String batchName = "Test-Batch";
        properties2.put("applitools.batch", batchName);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(batchName, ConfigFactory.create(ApplitoolsConfiguration.class).batch());
    }

    @Test
    public void testThrowException()
    {
        final String throwException = "true";
        properties2.put("applitools.throwException", throwException);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(Boolean.parseBoolean(throwException), ConfigFactory.create(ApplitoolsConfiguration.class).throwException());
    }

    @Test
    public void testHideCaret()
    {
        final String hideCaret = "false";
        properties2.put("applitools.hideCaret", hideCaret);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(Boolean.parseBoolean(hideCaret), ConfigFactory.create(ApplitoolsConfiguration.class).hideCaret());
    }

    @Test
    public void testWaitBeforeScreenshot()
    {
        final String waitBeforeScreenshot = "300";
        properties2.put("applitools.waitBeforeScreenshot", waitBeforeScreenshot);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        Assert.assertEquals(Integer.parseInt(waitBeforeScreenshot), ConfigFactory.create(ApplitoolsConfiguration.class).waitBeforeScreenshot());
    }

    @Test
    public void testConfigurationGetCleared()
    {
        Result result = JUnitCore.runClasses(ConfigurationGetsCleared.class);
        checkPass(result, 2, 0, 0);
    }
}
