package util.applitools.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import util.applitools.ApplitoolsApi;
import util.applitools.ApplitoolsRunner;

@Browser("Chrome_1500x1000")
@RunWith(ApplitoolsRunner.class)
public class HomePageTest
{
    @Rule
    public TestName name = new TestName();

    @Test
    public void testHomePage()
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.openEyes(name.getMethodName());

        // if the page you test contains some dynamic content, like the page we test here, there are two options to
        // avoid failure of visual compare
        // first is to ignore the region with dynamic content in test manager
        // or you can set match level to LAYOUT2 to make visual compare concentrate on page layout and not on the
        // content of the page
        ApplitoolsApi.setMatchLevel("LAYOUT2");

        // use this method to compare current page outlook with baseline
        // the parameter 'pageDescription' only defines how screenshot will be named in test manager and doesn't
        // influence baseline choice
        ApplitoolsApi.assertPage("Home Page");

        // use this method to compare current outlook of specific element with baseline
        // the parameter 'imageDescription' only defines how screenshot will be named in test manager and doesn't
        // influence baseline choice
        ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
    }

    @Test
    public void testHomePage2()
    {
        Selenide.open("https://www.xceptance.com/en/");
        ApplitoolsApi.openEyes(name.getMethodName());

        // if the page you test contains some dynamic content, like the page we test here, there are two options to
        // avoid failure of visual compare
        // first is to ignore the region with dynamic content in test manager
        // or you can set match level to LAYOUT2 to make visual compare concentrate on page layout and not on the
        // content of the page
        ApplitoolsApi.setMatchLevel("LAYOUT2");

        // use this method to compare current page outlook with baseline
        // the parameter 'pageDescription' only defines how screenshot will be named in test manager and doesn't
        // influence baseline choice
        ApplitoolsApi.assertPage("Home Page-2");

        // use this method to compare current outlook of specific element with baseline
        // the parameter 'imageDescription' only defines how screenshot will be named in test manager and doesn't
        // influence baseline choice
        // ApplitoolsApi.assertElement(By.cssSelector("#navigation"), "top navigation menu");
    }
}
