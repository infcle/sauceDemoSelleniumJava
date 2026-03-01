package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Project: sauceDemoSelleniumJava File: CheckoutPage
 * Description: Page Object for checkout step one.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
public class CheckoutPage extends BasePage {

    private static final String STEP_ONE_URL_PART = "checkout-step-one.html";

    private static final By CHECKOUT_TITLE = By.className("title");
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By ZIP_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");

    @Step("Wait for checkout step one page")
    public CheckoutPage waitForStepOnePage() {
        waitForUrlContains(STEP_ONE_URL_PART);
        return this;
    }

    @Step("Get checkout step one title")
    public String getTitle() {
        return getText(CHECKOUT_TITLE);
    }

    @Step("Enter first name: {firstName}")
    public CheckoutPage enterFirstName(String firstName) {
        enterText(FIRST_NAME, firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public CheckoutPage enterLastName(String lastName) {
        enterText(LAST_NAME, lastName);
        return this;
    }

    @Step("Enter ZIP code: {zipCode}")
    public CheckoutPage enterZipCode(String zipCode) {
        enterText(ZIP_CODE, zipCode);
        return this;
    }

    @Step("Read first name value")
    public String getFirstNameValue() {
        return getValue(FIRST_NAME);
    }

    @Step("Read last name value")
    public String getLastNameValue() {
        return getValue(LAST_NAME);
    }

    @Step("Read ZIP code value")
    public String getZipCodeValue() {
        return getValue(ZIP_CODE);
    }

    @Step("Fill checkout step one form and continue")
    public CheckoutOverviewPage fillInformationAndContinue(String firstName, String lastName, String zipCode) {
        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterZipCode(zipCode)
                .clickOnContinue();
    }

    @Step("Continue to checkout step two")
    public CheckoutOverviewPage clickOnContinue() {
        clickElement(CONTINUE_BUTTON);
        return new CheckoutOverviewPage();
    }
}
