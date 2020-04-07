package util.applitools.tests.validkey;

import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.Neodymium;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class AssertElementTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testAssertElement()
    {
        Selenide.open(Neodymium.configuration().url());
        final String testName = "assert element test";
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
        ApplitoolsApi.endAssertions();
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
        testManger.validateTestContainsScreenshots(batchName, testName, 1);
    }
}
