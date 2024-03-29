package util.applitools.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

import com.google.common.base.Joiner;
import com.xceptance.neodymium.NeodymiumRunner;

import util.applitools.ApplitoolsApi;

@RunWith(NeodymiumRunner.class)
public abstract class AbstractTest
{
    protected List<File> tempFiles = new LinkedList<>();

    protected Map<String, String> configProperties = new HashMap<>();

    protected final String configFileLocation = "config/temp-applitools.properties";

    protected File tempConfigFile = new File("./" + configFileLocation);

    @Before
    public void setupApplitoolsConfiguration()
    {
        tempFiles.add(tempConfigFile);
    }

    @After
    public void cleanup()
    {
        try
        {
            ApplitoolsApi.closeEyes();
        }
        catch (RuntimeException e)
        {

        }
        for (File tempFile : tempFiles)
        {
            deleteTempFile(tempFile);
        }
    }

    public void check(Result result, boolean expectedSuccessful, int expectedRunCount, int expectedIgnoreCount, int expectedFailCount,
                      String expectedFailureMessage)
    {
        Assert.assertEquals("Test successful", expectedSuccessful, result.wasSuccessful());
        Assert.assertEquals("Method run count", expectedRunCount, result.getRunCount());
        Assert.assertEquals("Method ignore count", expectedIgnoreCount, result.getIgnoreCount());
        Assert.assertEquals("Method fail count", expectedFailCount, result.getFailureCount());

        if (expectedFailureMessage != null)
        {
            Assert.assertTrue("Failure count", expectedFailCount == 1);
            Assert.assertEquals("Failure message", expectedFailureMessage, result.getFailures().get(0).getMessage());
        }
    }

    public void checkPass(Result result, int expectedRunCount, int expectedIgnoreCount, int expectedFailCount)
    {
        check(result, true, expectedRunCount, expectedIgnoreCount, expectedFailCount, null);
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

    public static void writeMapToPropertiesFile(Map<String, String> map, File file)
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
