package util.applitools.tests.validkey;

import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.Neodymium;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class AssertPageTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testAssertPage()
    {
        Selenide.open(Neodymium.configuration().url());
        final String testName = "assert page test";
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.assertPage("Homepage");
        ApplitoolsApi.closeEyes();
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
        testManger.validateTestContainsScreenshots(batchName, testName, 1);
    }
}
