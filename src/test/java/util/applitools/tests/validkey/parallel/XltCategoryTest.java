package util.applitools.tests.validkey.parallel;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.validkey.AbstractValidKeyTest;

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
