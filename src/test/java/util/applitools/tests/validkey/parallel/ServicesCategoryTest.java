package util.applitools.tests.validkey.parallel;

import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.Neodymium;

import util.applitools.ApplitoolsApi;
import util.applitools.tests.validkey.AbstractValidKeyTest;

public class ServicesCategoryTest extends AbstractValidKeyTest
{
    @Test
    public void testCategoryServices()
    {
        Selenide.open(Neodymium.configuration().url() + "/en/services/");
        ApplitoolsApi.openEyes("category services");
        ApplitoolsApi.assertPage("Category Services Page");
    }
}
