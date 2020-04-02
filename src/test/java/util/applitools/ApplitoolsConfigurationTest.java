package util.applitools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.applitools.eyes.MatchLevel;
import com.google.common.base.Joiner;

import util.applitools.ApplitoolsApi;
import util.applitools.ApplitoolsConfiguration;

public class ApplitoolsConfigurationTest extends AbstractTest
{
    private List<File> tempFiles = new LinkedList<>();

    private Map<String, String> properties2 = new HashMap<>();

    private final String fileLocation = "config/temp-applitools.properties";

    private File tempConfigFile2 = new File("./" + fileLocation);

    @Before
    public void setupApplitoolsConfiguration()
    {
        tempFiles.add(tempConfigFile2);
    }

    @After
    public void cleanup()
    {
        super.cleanup();
        for (File tempFile : tempFiles)
        {
            deleteTempFile(tempFile);
        }
    }

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

    /**
     * delete a temporary test file
     */
    private static void deleteTempFile(File tempFile)
    {
        if (tempFile.exists())
        {
            try
            {
                Files.delete(tempFile.toPath());
            }
            catch (Exception e)
            {
                System.out.println(MessageFormat.format("Couldn''t delete temporary file: ''{0}'' caused by {1}",
                                                        tempFile.getAbsolutePath(), e));
            }
        }
    }

    private static void writeMapToPropertiesFile(Map<String, String> map, File file)
    {
        try
        {
            String join = Joiner.on("\r\n").withKeyValueSeparator("=").join(map);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(join.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
