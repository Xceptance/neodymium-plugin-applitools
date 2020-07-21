package util.applitools.tests.validkey.setuptest;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.AbstractTest;

@Browser("Chrome_headless")
public abstract class AbstractNeodymiumApplitoolsSetupTest extends AbstractTest
{
    protected static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:")
    {
        {
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    };

    @Rule
    public TestName name = new TestName();

    @Test
    public void testOpenedEyes()
    {
        Assert.assertEquals(ApplitoolsApi.getEyes().getConfiguration().getTestName(), name.getMethodName());
    }
}
