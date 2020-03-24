package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.runner.RunWith;

import com.xceptance.neodymium.NeodymiumRunner;

@RunWith(NeodymiumRunner.class)
public abstract class AbstractTest
{

    protected void writePropertiy(String filename, String name, String value) throws IOException
    {
        OutputStream output = new FileOutputStream(filename, true);

        Properties prop = new Properties();
        // set the properties value
        prop.setProperty(name, value);

        // save properties to project root folder
        prop.store(output, null);
    }

    protected String readPropertiy(String filename, String name) throws IOException
    {

        InputStream input = new FileInputStream(filename);
        Properties prop = new Properties();

        // load a properties file
        prop.load(input);
        return prop.getProperty(name);
    }
}
