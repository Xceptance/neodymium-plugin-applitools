package util.applitools.tests.validkey;

import org.junit.After;

import pageobjects.ApplitoolsLoginPage;

public abstract class AbstractDeleteBatchAfterTest extends AbstractValidKeyTest
{
    @After
    public void deleteBatch()
    {
        new ApplitoolsLoginPage().login(username, password).deleteAllBatchesWithName(batchName);
    }
}
