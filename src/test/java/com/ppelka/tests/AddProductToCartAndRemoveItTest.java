package com.ppelka.tests;

import com.ppelka.pageobjects.CartPage;
import com.ppelka.pageobjects.ProductCatalog;
import com.ppelka.steps.CartSteps;
import com.ppelka.steps.ProductCatalogSteps;
import org.testng.annotations.Test;
import com.ppelka.testbase.BaseTest;

/**
 * End-to-end test verifying that a product can be added to the cart
 * and successfully removed afterward.
 */
public class AddProductToCartAndRemoveItTest extends BaseTest {

    @Test(description = "Add product to cart and remove it")
    public void addAndRemoveProductTest() {

        // Login and navigate to product catalog
        ProductCatalog catalog = loginSteps
                .openLoginPage()
                .loginValid("standard_user", "secret_sauce");

        productSteps = new ProductCatalogSteps(catalog);

        // Add product and verify cart quantity
        productSteps
                .addProduct("Sauce Labs Backpack")
                .verifyCartQuantity("1");

        // Navigate to cart
        CartPage cartPage = productSteps.goToCart();
        cartSteps = new CartSteps(cartPage);

        // Verify product, remove it, and confirm cart is empty
        cartSteps
                .verifyProductVisible("Sauce Labs Backpack")
                .removeProduct("Sauce Labs Backpack")
                .verifyCartIsEmpty();
    }
}
