package util.applitools.tests;

import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;

import com.applitools.eyes.MatchLevel;

import util.applitools.ApplitoolsApi;

public class ApplitoolsApiTest extends AbstractTest
{
    @Test
    public void testSetMatchLevel()
    {
        ApplitoolsApi.setMatchLevel("NONE");
        Assert.assertEquals(ApplitoolsApi.getEyes().getConfiguration().getMatchLevel(), MatchLevel.NONE);
    }

    @Test
    public void testSetHideCaretToFalse()
    {
        final boolean hideCaret = false;
        ApplitoolsApi.setHideCaret(hideCaret);
        Assert.assertEquals(ApplitoolsApi.getEyes().getHideCaret(), hideCaret);
    }

    @Test
    public void testSetHideCaretToTrue()
    {
        final boolean hideCaret = true;
        ApplitoolsApi.setHideCaret(hideCaret);
        Assert.assertEquals(ApplitoolsApi.getEyes().getHideCaret(), hideCaret);
    }

    @Test
    public void testSetWaitBeforeScreenshot()
    {
        final int waitMilisec = 3000;
        ApplitoolsApi.setWaitBeforeScreenshot(waitMilisec);
        Assert.assertEquals(ApplitoolsApi.getEyes().getWaitBeforeScreenshots(), waitMilisec);
    }

    @Test
    public void testSetupGlobalWithInvalidApiKey()
    {
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");
        final String matchLevel = "NONE";
        final String batchName = "Test Batch";

        properties2.put("applitools.apiKey", invalidApiKey);
        properties2.put("applitools.matchLevel", matchLevel);
        properties2.put("applitools.batch", batchName);

        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);

        ApplitoolsApi.setupGlobal();
        Assert.assertEquals(MatchLevel.NONE, ApplitoolsApi.getEyes().getConfiguration().getMatchLevel());
        Assert.assertEquals(batchName, ApplitoolsApi.getEyes().getBatch().getName());
        Assert.assertEquals(invalidApiKey, ApplitoolsApi.getEyes().getApiKey());
    }

    @Test
    public void testSetupGlobaWithEmptyOptionalProperties()
    {
        final String invalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");
        properties2.put("applitools.apiKey", invalidApiKey);
        writeMapToPropertiesFile(properties2, tempConfigFile2);
        ConfigFactory.setProperty(ApplitoolsApi.TEMPORARY_CONFIG_FILE_PROPERTY_NAME, "file:" + fileLocation);
        ApplitoolsApi.setupGlobal();
    }
}
