package tests.validkay.parallel;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import tests.validkay.AbstractValidKeyTest;
import util.applitools.ApplitoolsApi;

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
