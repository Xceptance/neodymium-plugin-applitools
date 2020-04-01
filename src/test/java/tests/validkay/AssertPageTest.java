package tests.validkay;

import java.io.IOException;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class AssertPageTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testAssertPage() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String testName = "assert page test";
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.assertPage("Homepage");
        ApplitoolsApi.endAssertions();
        Selenide.open("https://applitools.com/users/login");
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
        testManger.validateTestContainsScreenshots(batchName, testName, 1);
    }
}
