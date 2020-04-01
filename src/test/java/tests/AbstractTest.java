package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.runner.RunWith;

import com.xceptance.neodymium.NeodymiumRunner;

import util.applitools.ApplitoolsApi;

@RunWith(NeodymiumRunner.class)
public abstract class AbstractTest
{
    protected List<File> filesToDelete = new ArrayList<File>();

    @After
    public synchronized void cleanup()
    {
        filesToDelete.forEach(file -> file.delete());
        try
        {
            ApplitoolsApi.endAssertions();
        }
        catch (RuntimeException e)
        {

        }
    }
}
