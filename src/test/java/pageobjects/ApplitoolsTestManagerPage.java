package pageobjects;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import util.ApplitoolsTestHelper;

public class ApplitoolsTestManagerPage
{
    private final ElementsCollection batchNames = $$(".callout-title");

    private final ElementsCollection testContainters = $$(".change-indication-label .ellipsis");

    private final SelenideElement chatFrame = $("iframe[name='intercom-messenger-frame']");

    private final String screenshotsSelector = ".clickable.overlay";

    public ApplitoolsTestManagerPage isExpectedPage()
    {
        $(".logo.clickable").waitUntil(visible, 100000);
        if (ApplitoolsTestHelper.optionalWaitUntilCondition(chatFrame, visible,
                ApplitoolsTestHelper.standardWaitingTime))
        {
            Selenide.switchTo().frame(chatFrame);
            $(".intercom-messenger div[aria-label='Close']").click();
            Selenide.switchTo().defaultContent();
        }
        return this;
    }

    @Step("delete all batches with name '{name}'")
    public ApplitoolsTestManagerPage deleteAllBatchesWithName(String name)
    {
        batchNames.shouldHave(sizeGreaterThan(0));
        batchNames.filterBy(exactText(name)).forEach(batch ->
        {
            var checkbox = batch.closest(".list-item").find(".checkbox.default");
            checkbox.hover();
            checkbox.click();
            checkbox.waitUntil(cssClass("checked"), 10000);
        });
        $(".ai-trash").click();
        $("button[data-test='modal-confirm']").click();
        return this;
    }

    public ApplitoolsTestManagerPage validateNumberOfBatchesWithName(String batchName, int numberOfBatches)
    {
        batchNames.filterBy(exactText(batchName)).shouldHaveSize(numberOfBatches);
        return this;
    }

    public ApplitoolsTestManagerPage validateBatchContainsTest(String batchName, String testName)
    {
        batchNames.findBy(exactText(batchName)).click();
        testContainters.findBy(exactText(testName)).shouldBe(visible);
        return this;
    }

    public ApplitoolsTestManagerPage validateTestContainsScreenshots(String batchName, String testName,
            int screenshotAmount)
    {
        batchNames.findBy(exactText(batchName)).click();
        SelenideElement testContainer = testContainters.findBy(exactText(testName));
        if (!ApplitoolsTestHelper.optionalWaitUntilCondition($(screenshotsSelector), visible,
                ApplitoolsTestHelper.standardWaitingTime))
        {
            testContainer.click();
        }
        $$(screenshotsSelector).shouldHaveSize(screenshotAmount);
        return this;
    }
}
