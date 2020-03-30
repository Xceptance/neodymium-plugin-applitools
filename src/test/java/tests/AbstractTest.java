package tests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
    protected List<File> filesToDelete = new ArrayList<File>();

    @After
    public synchronized void cleanup()
    {
        filesToDelete.forEach(file -> file.delete());
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
