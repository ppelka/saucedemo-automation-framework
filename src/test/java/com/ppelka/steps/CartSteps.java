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

    /**
     * Verifies that a product is visible in the cart.
     */
    @Step("Verify product visible in cart: {productName}")
    public CartSteps verifyProductVisible(String productName) {
        log.info("Verifying product visible in cart: {}", productName);
        Assert.assertTrue(
                cartPage.isProductVisible(productName),
                "Product not visible in cart: " + productName
        );
        return this;
    }

    /**
     * Verifies that the cart is empty.
     */
    @Step("Verify cart is empty")
    public CartSteps verifyCartIsEmpty() {
        log.info("Verifying cart is empty");
        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart is not empty"
        );
        return this;
    }

    // ============================================================
    // Action steps
    // ============================================================

    /**
     * Removes a product from the cart.
     */
    @Step("Remove product from cart: {productName}")
    public CartSteps removeProduct(String productName) {
        log.info("Removing product from cart: {}", productName);
        cartPage.removeProduct(productName);
        return this;
    }

    /**
     * Proceeds from the cart page to the checkout information page.
     */
    @Step("Proceed to checkout information page")
    public CheckoutInformationPage proceedToCheckout() {
        log.info("Proceeding to checkout information page");
        return cartPage.proceedToCheckout();
    }
}
