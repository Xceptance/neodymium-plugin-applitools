package util.applitools.tests.validkey;

import java.io.IOException;

import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class AssertElementTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testAssertElement() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        final String testName = "assert element test";
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
        ApplitoolsApi.endAssertions();
        Selenide.open("https://applitools.com/users/login");
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
        testManger.validateTestContainsScreenshots(batchName, testName, 1);
    }
}
