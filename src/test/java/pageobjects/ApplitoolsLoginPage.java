package pageobjects;

import static com.codeborne.selenide.Selenide.$;

public class ApplitoolsLoginPage
{
    public ApplitoolsTestManagerPage login(String email, String password)
    {
        $("#email").val(email);
        $("#password").val(password);
        $(".btn-call-to-action").click();
        return new ApplitoolsTestManagerPage().isExpectedPage();
    }
}
