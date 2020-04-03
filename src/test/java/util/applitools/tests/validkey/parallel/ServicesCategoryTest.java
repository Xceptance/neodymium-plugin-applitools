package util.applitools.tests.validkey.parallel;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.validkey.AbstractValidKeyTest;

public class ServicesCategoryTest extends AbstractValidKeyTest
{
    @Test
    public void testCategoryServices()
    {
        Selenide.open("https://www.xceptance.com/en/services/");
        ApplitoolsApi.openEyes("category services");
        ApplitoolsApi.assertPage("Category Services Page");
    }
}
