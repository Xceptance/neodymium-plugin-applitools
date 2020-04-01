package tests.validkay;

import java.io.IOException;

import org.junit.Test;

import util.applitools.ApplitoolsApi;

public class OpenEyesTest extends AbstractValidKeyTest
{
    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        ApplitoolsApi.setupGroupingOfTestsByName(batchName);
        ApplitoolsApi.openEyes("open eyes test");
    }
}
