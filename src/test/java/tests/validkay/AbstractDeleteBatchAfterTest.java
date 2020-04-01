package tests.validkay;

import org.junit.After;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;

public abstract class AbstractDeleteBatchAfterTest extends AbstractValidKeyTest
{
    @After
    public void deleteBatch()
    {
        Selenide.open("https://applitools.com/users/login");
        Selenide.clearBrowserCookies();
        Selenide.refresh();
        new ApplitoolsLoginPage().login(username, password).deleteAllBatchesWithName(batchName);
    }
}
