package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * Project: sauceDemoSelleniumJava File: InventoryPage
 * Description: Page Object for inventory page validations.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
public class InventoryPage extends BasePage {

    private static final By PAGE_TITLE = By.className("title");

    @Step("Validate inventory page is visible")
    public void shouldBeVisible() {
        Assert.assertEquals(getText(PAGE_TITLE), "Products", "Inventory page is not visible.");
    }
}
