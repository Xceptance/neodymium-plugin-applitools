package tests.validkay;

import org.junit.After;
import org.junit.Test;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;

public class OneBatchOnParallelTest extends AbstractValidKeyTest
{
    @Test
    public void testOnlyOneBatchCreated()
    {
        Selenide.open("https://applitools.com/users/login");
        new ApplitoolsLoginPage().login(username, password).validateNumberOfBatchesWithName(batchName, 1);
    }

    @After
    public void endAssertionsAndCleanTestManager()
    {
        Selenide.clearBrowserCookies();
        Selenide.open("https://applitools.com/users/login");
        new ApplitoolsLoginPage().login(username, password).deleteAllBatchesWithName(batchName);
    };
}
