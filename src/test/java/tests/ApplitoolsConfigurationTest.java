package tests;

import java.io.IOException;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import util.applitools.ApplitoolsApi;

public class ApplitoolsConfigurationTest extends AbstractTest
{
    @Test
    public void testProjectName() throws IOException
    {
        final String projectName = "Test-Project";
        ApplitoolsApi.getConfiguration().setProperty("applitools.projectName", projectName);
        Assert.assertEquals(projectName, ApplitoolsApi.getConfiguration().projectName());
    }

    @Test
    public void testMatchLevel() throws IOException
    {
        final String matchLevel = "NONE";
        ApplitoolsApi.getConfiguration().setProperty("applitools.matchLevel", matchLevel);
        Assert.assertEquals(matchLevel, ApplitoolsApi.getConfiguration().matchLevel());
    }

    @Test
    public void testApiKey() throws IOException
    {
        final String apiKey = UUID.randomUUID().toString();
        ApplitoolsApi.getConfiguration().setProperty("applitools.apiKey", apiKey);

        Assert.assertEquals(apiKey, ApplitoolsApi.getConfiguration().apiKey());
    }

    @Test
    public void testBatch() throws IOException
    {
        final String batchName = "Test-Batch";
        ApplitoolsApi.getConfiguration().setProperty("applitools.batch", batchName);

        Assert.assertEquals(batchName, ApplitoolsApi.getConfiguration().batch());
    }

    @Test
    public void testThrowException() throws IOException
    {
        final String throwException = "true";
        ApplitoolsApi.getConfiguration().setProperty("applitools.throwException", throwException);

        Assert.assertEquals(throwException, ApplitoolsApi.getConfiguration().throwException());
    }

}
