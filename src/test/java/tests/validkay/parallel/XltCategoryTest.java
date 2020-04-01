package tests.validkay.parallel;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import tests.validkay.AbstractValidKeyTest;
import util.applitools.ApplitoolsApi;

public class XltCategoryTest extends AbstractValidKeyTest
{
    @Test
    public void testCategoryXlt()
    {
        Selenide.open("https://www.xceptance.com/en/xlt/");
        ApplitoolsApi.openEyes("category xlt");
        ApplitoolsApi.assertPage("Category Xlt Page");
    }
}
