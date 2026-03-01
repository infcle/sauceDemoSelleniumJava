package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import com.ecl.saucedemoselleniumjava.model.ProductData;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Project: sauceDemoSelleniumJava File: CartPage
 * Description: Page Object for cart actions and validations.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
public class CartPage extends BasePage {

    private static final By CART_TITLE = By.className("title");
    private static final By CHECKOUT_BUTTON = By.id("checkout");

    @Step("Get cart page title")
    public String getTitle() {
        return getText(CART_TITLE);
    }

    @Step("Get product data from cart for: {productName}")
    public ProductData getProductDataByName(String productName) {
        String containerXpath = "//div[@data-test='inventory-item-name' and normalize-space()="
                + xpathLiteral(productName)
                + "]/ancestor::div[@data-test='inventory-item']";

        String name = getText(By.xpath(containerXpath + "//div[@data-test='inventory-item-name']"));
        String description = getText(By.xpath(containerXpath + "//div[@data-test='inventory-item-desc']"));
        String price = getText(By.xpath(containerXpath + "//div[@data-test='inventory-item-price']"));

        return new ProductData(name, description, price);
    }

    @Step("Get quantity in cart for product: {productName}")
    public int getItemQuantityByName(String productName) {
        String containerXpath = "//div[@data-test='inventory-item-name' and normalize-space()="
                + xpathLiteral(productName)
                + "]/ancestor::div[@data-test='inventory-item']";

        String qty = getText(By.xpath(containerXpath + "//div[@data-test='item-quantity']"));
        return Integer.parseInt(qty.trim());
    }

    @Step("Click Checkout from cart")
    public CheckoutPage clickOnCheckout() {
        clickElement(CHECKOUT_BUTTON);
        return new CheckoutPage();
    }
}
