package tests;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.applitools.eyes.MatchLevel;

import util.applitools.ApplitoolsApi;

@Ignore
public class ApplitoolsApiTest extends AbstractTest
{

    @After
    public void deleteDevApplitoolsProperiesFile()
    {
        new File("config/dev-applitools.properties").delete();
    }

    @Test
    public void testSetMatchLevel() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        ApplitoolsApi.setMatchLevel("NONE");
        Assert.assertEquals(getApplitoolsEyes().getMatchLevel(), MatchLevel.NONE);
    }

    @Test
    public void testSetHideCaret() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        final boolean hideCaret = false;
        ApplitoolsApi.setHideCaret(hideCaret);
        Assert.assertEquals(getApplitoolsEyes().getHideCaret(), hideCaret);
    }

    @Test
    public void testSetWaitBeforeScreenshot() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        final int waitMilisec = 3000;
        ApplitoolsApi.setWaitBeforeScreenshot(waitMilisec);
        Assert.assertEquals(getApplitoolsEyes().getWaitBeforeScreenshots(), waitMilisec);
    }

    @Test
    public void testSetupGlobalWithInvalidApiKey() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException
    {
        final String invalidApiKey = randomInvalidApiKey();
        final String matchLevel = "NONE";
        final String batchName = "Test Batch";
        writePropertiy(devPropertiesFile, "applitools.apiKey", invalidApiKey);
        writePropertiy(devPropertiesFile, "applitools.matchLevel", matchLevel);
        writePropertiy(devPropertiesFile, "applitools.batch", batchName);
        ApplitoolsApi.setupGlobal();

        Assert.assertEquals(MatchLevel.NONE, getApplitoolsEyes().getMatchLevel());
        Assert.assertEquals(batchName, getApplitoolsEyes().getBatch().getName());
        Assert.assertEquals(invalidApiKey, getApplitoolsEyes().getApiKey());
    }

    @Test
    public void testSetupGlobaWithEmptyOptionalProperties() throws IOException
    {
        final String invalidApiKey = randomInvalidApiKey();
        writePropertiy(devPropertiesFile, "applitools.apiKey", invalidApiKey);
        ApplitoolsApi.setupGlobal();
    }

}
