package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Project: sauceDemoSelleniumJava File: CheckoutCompletePage
 * Description: Page Object for checkout completion page.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 01/03/2026
 */
public class CheckoutCompletePage extends BasePage {

    private static final String COMPLETE_URL_PART = "checkout-complete.html";

    private static final By COMPLETE_TITLE = By.className("title");
    private static final By COMPLETE_HEADER = By.className("complete-header");
    private static final By COMPLETE_TEXT = By.className("complete-text");

    @Step("Wait for checkout complete page")
    public CheckoutCompletePage waitForCompletePage() {
        waitForUrlContains(COMPLETE_URL_PART);
        return this;
    }

    @Step("Get checkout complete title")
    public String getTitle() {
        return getText(COMPLETE_TITLE);
    }

    @Step("Get completion header")
    public String getCompleteHeader() {
        return getText(COMPLETE_HEADER);
    }

    @Step("Get completion message")
    public String getCompleteMessage() {
        return getText(COMPLETE_TEXT);
    }
}
