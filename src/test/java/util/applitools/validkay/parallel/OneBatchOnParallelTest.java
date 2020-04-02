package util.applitools.validkay.parallel;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import pageobjects.ApplitoolsLoginPage;
import tests.validkay.AbstractDeleteBatchAfterTest;

public class OneBatchOnParallelTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testOnlyOneBatchCreated()
    {
        Selenide.open("https://applitools.com/users/login");
        new ApplitoolsLoginPage().login(username, password).validateNumberOfBatchesWithName(batchName, 1);
    }
}