package tests.validkay;

import java.io.IOException;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import util.applitools.ApplitoolsApi;

public class OpenEyesTest extends AbstractDeleteBatchAfterTest
{
    @Test
    public void testOpenEyesWithValidApiKey() throws IOException
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.openEyes("open eyes test");
    }
}
