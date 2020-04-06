package pageobjects;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;

public class ApplitoolsLoginPage
{
    public ApplitoolsTestManagerPage login(String email, String password)
    {
        Selenide.open("https://applitools.com/users/login");
        Selenide.clearBrowserCookies();
        Selenide.refresh();
        $("#email").val(email);
        $("#password").val(password);
        $(".btn-call-to-action").click();
        return new ApplitoolsTestManagerPage().isExpectedPage();
    }
}
