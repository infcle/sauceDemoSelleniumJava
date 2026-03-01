package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Project: sauceDemoSelleniumJava File: LoginPage
 * Description: Page Object for SauceDemo authentication actions.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
public class LoginPage extends BasePage {

    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");

    @Step("Open login page")
    public LoginPage openLoginPage() {
        openPath("/");
        return this;
    }

    @Step("Type username: {username}")
    public LoginPage typeUsername(String username) {
        enterText(USERNAME_INPUT, username);
        return this;
    }

    @Step("Type password")
    public LoginPage typePassword(String password) {
        enterText(PASSWORD_INPUT, password);
        return this;
    }

    @Step("Click Login and go to Inventory page")
    public InventoryPage clickLogin() {
        clickElement(LOGIN_BUTTON);
        return new InventoryPage();
    }

    @Step("Login with user: {username}")
    public ProductPage login(String username, String password) {
        return openLoginPage()
                .typeUsername(username)
                .typePassword(password)
                .clickLoginAsProductPage();
    }

    @Step("Click Login and go to Product page")
    public ProductPage clickLoginAsProductPage() {
        clickElement(LOGIN_BUTTON);
        return new ProductPage();
    }
}
