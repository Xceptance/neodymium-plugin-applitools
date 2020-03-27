package tests;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
}
