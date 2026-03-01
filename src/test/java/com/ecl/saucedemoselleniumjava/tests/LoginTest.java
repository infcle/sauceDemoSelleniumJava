package com.ecl.saucedemoselleniumjava.tests;

import com.ecl.saucedemoselleniumjava.base.BaseTest;
import com.ecl.saucedemoselleniumjava.config.TestConfig;
import com.ecl.saucedemoselleniumjava.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

/**
 * Project: sauceDemoSelleniumJava File: LoginTest
 * Description: Authentication smoke tests for SauceDemo.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
@Epic("SauceDemo Web UI")
@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test
    @Story("Valid login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Logs in with a valid user and verifies that the products page is displayed.")
    public void loginWithValidUser() {
        new LoginPage()
                .openLoginPage()
                .typeUsername(TestConfig.username())
                .typePassword(TestConfig.password())
                .clickLogin()
                .shouldBeVisible();
    }
}
