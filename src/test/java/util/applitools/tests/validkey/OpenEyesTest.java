package util.applitools.tests.validkey;

import java.io.IOException;

import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.Neodymium;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class OpenEyesTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        final String testName = "open eyes test";
        Selenide.open(Neodymium.configuration().url());
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.endAssertions();
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
    }
}
