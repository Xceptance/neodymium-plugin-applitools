package util.applitools.tests.validkey;

import java.io.IOException;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class OpenEyesTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        final String testName = "open eyes test";
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.endAssertions();
        Selenide.open("https://applitools.com/users/login");
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
    }
}
