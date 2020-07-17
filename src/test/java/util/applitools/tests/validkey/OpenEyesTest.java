package util.applitools.tests.validkey;

import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.Neodymium;

import pageobjects.ApplitoolsLoginPage;
import pageobjects.ApplitoolsTestManagerPage;
import util.applitools.ApplitoolsApi;

public class OpenEyesTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testOpenEyesWithValidApiKey()
    {
        final String testName = "open eyes test";
        Selenide.open(Neodymium.configuration().url());
        ApplitoolsApi.openEyes(testName);
        ApplitoolsApi.closeEyes();
        ApplitoolsTestManagerPage testManger = new ApplitoolsLoginPage().login(username, password);
        testManger.validateBatchContainsTest(batchName, testName);
    }
}
