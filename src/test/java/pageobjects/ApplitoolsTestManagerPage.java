package pageobjects;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class ApplitoolsTestManagerPage
{
    private final ElementsCollection batchNames = $$(".callout-title");

    private final ElementsCollection testContainters = $$(".data-table-body-scroller-content>div>.clickable>.cells-container>.data-table-cell>.flex-row>.ellipsis");

    public ApplitoolsTestManagerPage isExpectedPage()
    {
        $(".logo.clickable").waitUntil(visible, 100000);
        return this;
    }

    public ApplitoolsTestManagerPage deleteAllBatchesWithName(String name)
    {
        batchNames.shouldHave(sizeGreaterThan(0));
        batchNames.filterBy(exactText(name)).forEach(batch -> {
            batch.parent().parent().parent().find(".checkbox.default").hover();
            batch.parent().parent().parent().find(".checkbox.default").click();
            batch.parent().parent().parent().find(".checkbox.default.checked").waitUntil(visible, 10000);
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

    public ApplitoolsTestManagerPage validateTestContainsScreenshots(String batchName, String testName, int screenshotAmount)
    {
        batchNames.findBy(exactText(batchName)).click();
        SelenideElement testContainer = testContainters.findBy(exactText(testName));
        if (!testContainer.parent().parent().parent().parent().parent().parent().getCssValue("height").equals("315px"))
        {
            testContainer.click();
        }
        testContainer.parent().parent().parent().parent().parent().parent().parent().findAll(".clickable.overlay").shouldHaveSize(screenshotAmount);
        return this;
    }
}
