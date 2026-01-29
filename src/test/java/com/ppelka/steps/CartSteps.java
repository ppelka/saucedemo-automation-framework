package com.ppelka.steps;

import com.ppelka.pageobjects.CartPage;
import com.ppelka.pageobjects.CheckoutInformationPage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step definitions for cart-related actions.
 * Provides high-level business steps used in test scenarios.
 */
public class CartSteps {

    private static final Logger log = LoggerFactory.getLogger(CartSteps.class);

    private final CartPage cartPage;

    public CartSteps(CartPage cartPage) {
        this.cartPage = cartPage;
    }

    // ============================================================
    // Verification steps
    // ============================================================

    @Step("Verify product visible in cart: {productName}")
    public CartSteps verifyProductVisible(String productName) {
        log.info("Verifying product visible in cart: {}", productName);

        Assert.assertTrue(
                cartPage.isProductVisible(productName),
                "Expected product to be visible in cart: " + productName
        );

        return this;
    }

    @Step("Verify cart is empty")
    public CartSteps verifyCartIsEmpty() {
        log.info("Verifying cart is empty");

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart is not empty but should be"
        );

        return this;
    }

    // ============================================================
    // Action steps
    // ============================================================

    @Step("Remove product from cart: {productName}")
    public CartSteps removeProduct(String productName) {
        log.info("Removing product from cart: {}", productName);

        cartPage.removeProduct(productName);

        Assert.assertFalse(
                cartPage.isProductVisible(productName),
                "Product still visible in cart after removal: " + productName
        );

        return this;
    }

    @Step("Proceed to checkout information page")
    public CheckoutInformationPage proceedToCheckout() {
        log.info("Proceeding to checkout information page");

        CheckoutInformationPage infoPage = cartPage.proceedToCheckout();

        Assert.assertTrue(
                infoPage.isAt(),
                "Checkout Information page failed to load after navigation"
        );

        return infoPage;
    }
}