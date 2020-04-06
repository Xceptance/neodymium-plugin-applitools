package util.applitools.tests.validkey.parallel;

import org.junit.Test;

import pageobjects.ApplitoolsLoginPage;
import util.applitools.tests.validkey.AbstractDeleteBatchAfterTest;

public class OneBatchOnParallelTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testOnlyOneBatchCreated()
    {
        new ApplitoolsLoginPage().login(username, password).validateNumberOfBatchesWithName(batchName, 1);
    }
}