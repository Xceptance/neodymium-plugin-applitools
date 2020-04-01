package tests.validkay.parallel;

import static com.codeborne.selenide.Selenide.$;

import org.junit.Test;

import com.codeborne.selenide.Selenide;

import tests.validkay.AbstractValidKeyTest;
import util.applitools.ApplitoolsApi;

public class HomePageTest extends AbstractValidKeyTest
{
    @Test
    public void testHomepage()
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.openEyes("home page test");
        ApplitoolsApi.assertPage("Home Page");
    }

    @Test
    public void testHomepageWithCookies()
    {
        // Goto the home page
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.openEyes("home page with cookies test");

        // accept cookies
        $(".text-right .btn.btn-primary").click();

        // this can be moved to page object
        ApplitoolsApi.assertPage("Home Page after cookies accepted");
    }

}