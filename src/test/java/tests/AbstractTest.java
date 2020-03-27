package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.runner.RunWith;

import com.applitools.eyes.selenium.Eyes;
import com.xceptance.neodymium.NeodymiumRunner;

import util.applitools.ApplitoolsApi;

@RunWith(NeodymiumRunner.class)
public abstract class AbstractTest
{
    protected List<String> filesToDelete = new ArrayList<String>();

    protected final String devPropertiesFilename = "config/dev-applitools.properties";

    @After
    public synchronized void cleanup()
    {
        filesToDelete.forEach(file -> new File(file).delete());
        new File(devPropertiesFilename).delete();
    }

    protected synchronized void writePropertiy(String filename, String name, String value) throws IOException
    {
        if (name.contains("batch"))
        {
            filesToDelete.add(filename);
        }
        OutputStream output = new FileOutputStream(filename, true);

        Properties prop = new Properties();
        // set the properties value
        prop.setProperty(name, value);

        // save properties to project root folder
        prop.store(output, null);
    }

    protected synchronized String readPropertiy(String filename, String name) throws IOException
    {
        if (name.contains("batch"))
        {
            filesToDelete.add(filename);
        }
        InputStream input = new FileInputStream(filename);
        Properties prop = new Properties();

        // load a properties file
        prop.load(input);
        return prop.getProperty(name);
    }

    protected synchronized Eyes getApplitoolsEyes() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        Field privateStringField = ApplitoolsApi.class.getDeclaredField("eyes");

        privateStringField.setAccessible(true);

        ThreadLocal<Eyes> fieldValue = (ThreadLocal<Eyes>) privateStringField.get(null);
        privateStringField.setAccessible(false);
        return fieldValue.get();
    }

    protected synchronized String randomInvalidApiKey()
    {
        String randomInvalidApiKey = UUID.randomUUID().toString().replaceAll("-", "");
        Random random = new Random();
        for (int i = 0; i < randomInvalidApiKey.length() / 2; i++)
        {
            int index = random.nextInt(randomInvalidApiKey.length() / 2);
            randomInvalidApiKey = randomInvalidApiKey.substring(0, index) + Character.toUpperCase(randomInvalidApiKey.charAt(index))
                                  + randomInvalidApiKey.substring(index + 1);
        }
        return randomInvalidApiKey;
    }

}
