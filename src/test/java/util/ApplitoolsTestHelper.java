package util;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class ApplitoolsTestHelper
{
    public static long shortWaitingTime = Neodymium.configuration().selenideTimeout() / 3;

    public static long standardWaitingTime = Neodymium.configuration().selenideTimeout();

    public static long doubleWaitingTime = Neodymium.configuration().selenideTimeout() * 2;

    public static long mediumWaitingTime = Neodymium.configuration().selenideTimeout() * 3;

    public static long longWaitingTime = Neodymium.configuration().selenideTimeout() * 10;

    public static long veryLongWaitingTime = Neodymium.configuration().selenideTimeout() * 20;

    @Step("wait for a specific condition of an element")
    public static boolean optionalWaitUntilCondition(SelenideElement element, Condition condition, long maxWaitingTime)
    {
        boolean result = false;
        final long start = System.currentTimeMillis();
        while (!result && ((System.currentTimeMillis() - start) < maxWaitingTime))
        {
            if (element.has(condition))
            {
                result = true;
                break;
            }
            Selenide.sleep(maxWaitingTime / 20);
        }
        return result;
    }

}
