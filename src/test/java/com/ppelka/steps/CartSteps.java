package com.ppelka.steps;

import com.ppelka.pageobjects.CartPage;
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

    /**
     * Verifies that a product with the given name is visible in the cart.
     */
    @Step("Verify that product \"{productName}\" is visible in the cart")
    public CartSteps verifyProductVisible(String productName) {
        log.info("Checking if product is visible in cart: {}", productName);

        Assert.assertTrue(
                cartPage.isProductVisible(productName),
                "Product not visible in cart: " + productName
        );
        return this;
    }

    /**
     * Removes a product from the cart by its name.
     */
    @Step("Remove product from cart: {productName}")
    public CartSteps removeProduct(String productName) {
        log.info("Removing product from cart: {}", productName);
        cartPage.removeProduct(productName);
        return this;
    }

    /**
     * Verifies that the cart contains no items.
     */
    @Step("Verify that the cart is empty")
    public CartSteps verifyCartIsEmpty() {
        log.info("Verifying that cart is empty");
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty");
        return this;
    }
}
