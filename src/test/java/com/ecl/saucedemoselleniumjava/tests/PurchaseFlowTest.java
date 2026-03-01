package com.ecl.saucedemoselleniumjava.tests;

import com.ecl.saucedemoselleniumjava.base.BaseTest;
import com.ecl.saucedemoselleniumjava.config.TestConfig;
import com.ecl.saucedemoselleniumjava.model.ProductData;
import com.ecl.saucedemoselleniumjava.pages.CartPage;
import com.ecl.saucedemoselleniumjava.pages.CheckoutCompletePage;
import com.ecl.saucedemoselleniumjava.pages.CheckoutOverviewPage;
import com.ecl.saucedemoselleniumjava.pages.CheckoutPage;
import com.ecl.saucedemoselleniumjava.pages.LoginPage;
import com.ecl.saucedemoselleniumjava.pages.ProductPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * Project: sauceDemoSelleniumJava File: PurchaseFlowTest
 * Description: End-to-end purchase flow from login to checkout completion.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
@Epic("SauceDemo Web UI")
@Feature("Checkout")
public class PurchaseFlowTest extends BaseTest {

    @Test
    @Story("Complete purchase with one product")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Validates cart item data, checkout step one and two, totals consistency, and completion message.")
    public void e2ePurchaseFlow() {
        String productName = "Sauce Labs Backpack";
        String firstName = TestConfig.checkoutFirstName();
        String lastName = TestConfig.checkoutLastName();
        String zipCode = TestConfig.checkoutZipCode();

        LoginPage loginPage = new LoginPage();
        ProductPage productPage = loginPage.login(TestConfig.username(), TestConfig.password());
        productPage.shouldBeVisible();

        ProductData expectedProduct = productPage.getProductDataByName(productName);
        productPage.addToCartByProductName(productName);

        Assert.assertEquals(productPage.getRemoveButtonTextByProductName(productName), "Remove", "Product was not added correctly.");
        Assert.assertEquals(productPage.getCartBadgeCount(), 1, "Cart badge count should be exactly 1.");

        CartPage cartPage = productPage.clickOnCartButton();
        Assert.assertEquals(cartPage.getTitle(), "Your Cart", "Cart page is not visible.");

        ProductData productInCart = cartPage.getProductDataByName(productName);
        Assert.assertEquals(productInCart, expectedProduct, "Product data in cart does not match expected data.");
        Assert.assertEquals(cartPage.getItemQuantityByName(productName), 1, "Cart item quantity should be 1.");

        CheckoutPage checkoutStepOnePage = cartPage.clickOnCheckout().waitForStepOnePage();
        Assert.assertEquals(checkoutStepOnePage.getTitle(), "Checkout: Your Information", "Checkout step one page is not visible.");

        checkoutStepOnePage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterZipCode(zipCode);

        Assert.assertEquals(checkoutStepOnePage.getFirstNameValue(), firstName, "First name value does not match.");
        Assert.assertEquals(checkoutStepOnePage.getLastNameValue(), lastName, "Last name value does not match.");
        Assert.assertEquals(checkoutStepOnePage.getZipCodeValue(), zipCode, "ZIP code value does not match.");

        CheckoutOverviewPage checkoutStepTwoPage = checkoutStepOnePage.clickOnContinue().waitForStepTwoPage();
        Assert.assertEquals(checkoutStepTwoPage.getTitle(), "Checkout: Overview", "Checkout overview page is not visible.");

        ProductData productInOverview = checkoutStepTwoPage.getProductDataByName(productName);
        Assert.assertEquals(productInOverview, expectedProduct, "Product data in checkout overview does not match expected data.");
        Assert.assertEquals(checkoutStepTwoPage.getItemQuantityByName(productName), 1, "Overview item quantity should be 1.");

        BigDecimal expectedItemTotal = new BigDecimal(expectedProduct.price().replace("$", ""));
        Assert.assertEquals(checkoutStepTwoPage.getItemTotalAmount(), expectedItemTotal, "Item total does not match selected product price.");
        Assert.assertTrue(checkoutStepTwoPage.isTotalConsistent(), "Total amount is not consistent: item total + tax must equal total.");

        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickOnFinish().waitForCompletePage();

        Assert.assertEquals(checkoutCompletePage.getTitle(), "Checkout: Complete!", "Checkout complete page title is not visible.");
        Assert.assertEquals(checkoutCompletePage.getCompleteHeader(), "Thank you for your order!", "Completion header text does not match.");
        Assert.assertEquals(
                checkoutCompletePage.getCompleteMessage(),
                "Your order has been dispatched, and will arrive just as fast as the pony can get there!",
                "Completion message text does not match."
        );
    }
}
