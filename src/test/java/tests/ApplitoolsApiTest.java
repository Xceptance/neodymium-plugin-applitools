package tests;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.applitools.eyes.MatchLevel;
import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;

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
        writePropertiy("config/dev-applitools.properties", "applitools.apiKey", invalidApiKey);
        writePropertiy("config/dev-applitools.properties", "applitools.matchLevel", matchLevel);
        writePropertiy("config/dev-applitools.properties", "applitools.batch", batchName);
        ApplitoolsApi.setupGlobal();

        Assert.assertEquals(MatchLevel.NONE, getApplitoolsEyes().getMatchLevel());
        Assert.assertEquals(batchName, getApplitoolsEyes().getBatch().getName());
        Assert.assertEquals(invalidApiKey, getApplitoolsEyes().getApiKey());
    }

    @Test
    public void testSetupGlobaWithEmptyOptionalProperties() throws IOException
    {
        final String invalidApiKey = randomInvalidApiKey();
        writePropertiy("config/dev-applitools.properties", "applitools.apiKey", invalidApiKey);
        ApplitoolsApi.setupGlobal();
    }

    @Browser("Chrome_headless")
    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        writePropertiy(devPropertiesFilename, "applitools.apiKey", System.getenv("API_KEY"));
        ApplitoolsApi.setupGlobal();
        ApplitoolsApi.openEyes("test");
    }
}
