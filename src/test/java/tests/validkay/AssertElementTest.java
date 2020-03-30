package tests.validkay;

import java.io.IOException;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class AssertElementTest extends AbstractValidKeyTest
{
    @Test
    public void testAssertElement() throws IOException
    {
        final String testName = "assert element test";
        ApplitoolsApi.setupForGroupOfTests(batchName);
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.assertElement("#navigation");
        ApplitoolsApi.endAssertions();
        Selenide.open("https://applitools.com/users/login");
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
        testManger.validateTestContainsScreenshots(batchName, testName, 1);
    }
}
