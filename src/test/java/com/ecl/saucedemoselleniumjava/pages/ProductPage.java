package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import com.ecl.saucedemoselleniumjava.model.ProductData;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * Project: sauceDemoSelleniumJava File: ProductPage
 * Description: Page Object for product listing actions and validations.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
public class ProductPage extends BasePage {

    private static final By PRODUCTS_TITLE = By.className("title");
    private static final By CART_BUTTON = By.id("shopping_cart_container");
    private static final By CART_BADGE = By.className("shopping_cart_badge");

    @Step("Get Products page title")
    public String getTitle() {
        return getText(PRODUCTS_TITLE);
    }

    @Step("Validate products page is visible")
    public void shouldBeVisible() {
        Assert.assertEquals(getTitle(), "Products", "Products page is not visible.");
    }

    @Step("Add product to cart: {productName}")
    public void addToCartByProductName(String productName) {
        By addToCartButton = By.id("add-to-cart-" + normalizeProductName(productName));
        clickElement(addToCartButton);
    }

    @Step("Get remove button text for product: {productName}")
    public String getRemoveButtonTextByProductName(String productName) {
        By removeButton = By.id("remove-" + normalizeProductName(productName));
        return getText(removeButton);
    }

    @Step("Get cart badge count")
    public int getCartBadgeCount() {
        return Integer.parseInt(getText(CART_BADGE));
    }

    @Step("Open cart")
    public CartPage clickOnCartButton() {
        clickElement(CART_BUTTON);
        return new CartPage();
    }

    @Step("Get product data from inventory for: {productName}")
    public ProductData getProductDataByName(String productName) {
        String containerXpath = "//div[@data-test='inventory-item-name' and normalize-space()="
                + xpathLiteral(productName)
                + "]/ancestor::div[@data-test='inventory-item']";

        String name = getText(By.xpath(containerXpath + "//div[@data-test='inventory-item-name']"));
        String description = getText(By.xpath(containerXpath + "//div[@data-test='inventory-item-desc']"));
        String price = getText(By.xpath(containerXpath + "//div[@data-test='inventory-item-price']"));

        return new ProductData(name, description, price);
    }

    private String normalizeProductName(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }
}
