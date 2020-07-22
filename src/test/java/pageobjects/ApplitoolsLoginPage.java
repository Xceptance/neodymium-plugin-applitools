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
        $("#email").sendKeys(email);
        Selenide.sleep(4000);
        $("#password").sendKeys(password);
        Selenide.sleep(4000);
        retypeCredentials(email, password);
        $(".btn-call-to-action").click();
        return new ApplitoolsTestManagerPage().isExpectedPage();
    }

    private void retypeCredentials(String email, String password)
    {
        if (!$("#email").getText().equals(email))
        {
            $("#email").clear();
            $("#email").sendKeys(email);
        }
        if (!$("#password").getText().equals(password))
        {
            $("#password").clear();
            $("#password").sendKeys(password);
        }
    }
}
