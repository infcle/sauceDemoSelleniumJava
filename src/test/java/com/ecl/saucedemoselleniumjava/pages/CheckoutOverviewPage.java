package com.ecl.saucedemoselleniumjava.pages;

import com.ecl.saucedemoselleniumjava.base.BasePage;
import com.ecl.saucedemoselleniumjava.model.ProductData;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.math.BigDecimal;

/**
 * Project: sauceDemoSelleniumJava File: CheckoutOverviewPage
 * Description: Page Object for checkout step two summary validations.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 01/03/2026
 */
public class CheckoutOverviewPage extends BasePage {

    private static final String STEP_TWO_URL_PART = "checkout-step-two.html";

    private static final By OVERVIEW_TITLE = By.className("title");
    private static final By SUBTOTAL_LABEL = By.className("summary_subtotal_label");
    private static final By TAX_LABEL = By.className("summary_tax_label");
    private static final By TOTAL_LABEL = By.className("summary_total_label");
    private static final By FINISH_BUTTON = By.id("finish");

    @Step("Wait for checkout step two page")
    public CheckoutOverviewPage waitForStepTwoPage() {
        waitForUrlContains(STEP_TWO_URL_PART);
        return this;
    }

    @Step("Get checkout overview title")
    public String getTitle() {
        return getText(OVERVIEW_TITLE);
    }

    @Step("Get product data from checkout overview for: {productName}")
    public ProductData getProductDataByName(String productName) {
        String containerXpath = "//div[@class='inventory_item_name' and normalize-space()="
                + xpathLiteral(productName)
                + "]/ancestor::div[@class='cart_item']";

        String name = getText(By.xpath(containerXpath + "//div[@class='inventory_item_name']"));
        String description = getText(By.xpath(containerXpath + "//div[@class='inventory_item_desc']"));
        String price = getText(By.xpath(containerXpath + "//div[@class='inventory_item_price']"));

        return new ProductData(name, description, price);
    }

    @Step("Get quantity in checkout overview for product: {productName}")
    public int getItemQuantityByName(String productName) {
        String containerXpath = "//div[@class='inventory_item_name' and normalize-space()="
                + xpathLiteral(productName)
                + "]/ancestor::div[@class='cart_item']";

        String qty = getText(By.xpath(containerXpath + "//div[@data-test='item-quantity']"));
        return Integer.parseInt(qty.trim());
    }

    @Step("Get item total amount")
    public BigDecimal getItemTotalAmount() {
        String label = getText(SUBTOTAL_LABEL);
        return parseAmount(label);
    }

    @Step("Get tax amount")
    public BigDecimal getTaxAmount() {
        String label = getText(TAX_LABEL);
        return parseAmount(label);
    }

    @Step("Get total amount")
    public BigDecimal getTotalAmount() {
        String label = getText(TOTAL_LABEL);
        return parseAmount(label);
    }

    @Step("Validate total is consistent: item total + tax = total")
    public boolean isTotalConsistent() {
        return getItemTotalAmount().add(getTaxAmount()).compareTo(getTotalAmount()) == 0;
    }

    @Step("Finish checkout")
    public CheckoutCompletePage clickOnFinish() {
        clickElement(FINISH_BUTTON);
        return new CheckoutCompletePage();
    }

    private BigDecimal parseAmount(String label) {
        String sanitized = label.replaceAll("[^0-9.]", "");
        return new BigDecimal(sanitized);
    }
}
