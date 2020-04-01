package tests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.applitools.eyes.MatchLevel;

import util.applitools.ApplitoolsApi;

public class ApplitoolsApiTest extends AbstractTest
{
    @Test
    public void testSetMatchLevel() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        ApplitoolsApi.setMatchLevel("NONE");
        Assert.assertEquals(ApplitoolsApi.getEyes().getMatchLevel(), MatchLevel.NONE);
    }

    @Test
    public void testSetHideCaret() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        final boolean hideCaret = false;
        ApplitoolsApi.setHideCaret(hideCaret);
        Assert.assertEquals(ApplitoolsApi.getEyes().getHideCaret(), hideCaret);
    }

    @Test
    public void testSetWaitBeforeScreenshot() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        final int waitMilisec = 3000;
        ApplitoolsApi.setWaitBeforeScreenshot(waitMilisec);
        Assert.assertEquals(ApplitoolsApi.getEyes().getWaitBeforeScreenshots(), waitMilisec);
    }

    @Test
    public void testSetupGlobalWithInvalidApiKey() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException
    {
        final String invalidApiKey = randomInvalidApiKey();
        final String matchLevel = "NONE";
        final String batchName = "Test Batch";
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", invalidApiKey);
        ApplitoolsApi.getConfiguration().setProperty("applitools.matchLevel", matchLevel);
        ApplitoolsApi.getConfiguration().setProperty("applitools.batch", batchName);
        ApplitoolsApi.setupGlobal();

        Assert.assertEquals(MatchLevel.NONE, ApplitoolsApi.getEyes().getMatchLevel());
        Assert.assertEquals(batchName, ApplitoolsApi.getEyes().getBatch().getName());
        Assert.assertEquals(invalidApiKey, ApplitoolsApi.getEyes().getApiKey());
    }

    @Test
    public void testSetupGlobaWithEmptyOptionalProperties() throws IOException
    {
        final String invalidApiKey = randomInvalidApiKey();
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", invalidApiKey);
        ApplitoolsApi.setupGlobal();
    }

}
