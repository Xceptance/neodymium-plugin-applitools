package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import util.applitools.BatchHelper;

public class BatchHelperTest extends AbstractTest
{
    private String filename;

    @After
    public void deleteFile()
    {
        new File(filename).delete();
    }

    @Test
    public void testGetBatch() throws IOException
    {
        final String batchName = "Test Batch";
        filename = "target/Test Batch.properties";
        writePropertiy(new File(filename), "batch." + batchName, batchName + new Date().toString());
        String batchId = BatchHelper.getBatch(batchName);
        Assert.assertNotNull(batchId);
        Assert.assertFalse(batchId.equals(""));
    }

    @Test
    public void testAddBatch() throws IOException
    {
        final String batchName = "Test Batch";
        filename = "target/Test Batch.properties";
        BatchHelper.addBatch(batchName);
        String batchId = readPropertiy(new File(filename), "batch." + batchName);
        Assert.assertNotNull(batchId);
        Assert.assertFalse(batchId.equals(""));
    }

    private synchronized void writePropertiy(File file, String name, String value) throws IOException
    {
        if (name.contains("batch"))
        {
            filesToDelete.add(file);
        }
        OutputStream output = new FileOutputStream(file, true);

        Properties prop = new Properties();
        // set the properties value
        prop.setProperty(name, value);

        // save properties to project root folder
        prop.store(output, null);
    }

    private synchronized String readPropertiy(File file, String name) throws IOException
    {
        if (name.contains("batch"))
        {
            filesToDelete.add(file);
        }
        InputStream input = new FileInputStream(file);
        Properties prop = new Properties();

        // load a properties file
        prop.load(input);
        return prop.getProperty(name);
    }

}
