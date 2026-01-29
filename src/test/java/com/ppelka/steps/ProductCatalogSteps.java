package com.ppelka.steps;

import com.ppelka.pageobjects.CartPage;
import com.ppelka.pageobjects.ProductCatalog;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step definitions for product catalog actions.
 * Provides high-level business steps used in test scenarios.
 */
public class ProductCatalogSteps {

    private static final Logger log = LoggerFactory.getLogger(ProductCatalogSteps.class);

    private final ProductCatalog catalog;

    public ProductCatalogSteps(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    // ============================================================
    // Actions
    // ============================================================

    @Step("Add product to cart: {productName}")
    public ProductCatalogSteps addProduct(String productName) {
        log.info("Adding product to cart: {}", productName);

        catalog.addProduct(productName);

        Assert.assertTrue(
                catalog.isRemoveButtonVisible(productName),
                "Product was not added to cart: remove button not visible for " + productName
        );

        return this;
    }

    @Step("Verify cart quantity is: {expected}")
    public ProductCatalogSteps verifyCartQuantity(String expected) {
        String actual = catalog.getCartQuantity();

        log.info("Verifying cart quantity. Expected: {}, Actual: {}", expected, actual);

        Assert.assertEquals(
                actual,
                expected,
                "Cart quantity mismatch. Expected: " + expected + ", Actual: " + actual
        );

        return this;
    }

    @Step("Navigate to cart page")
    public CartPage goToCart() {
        log.info("Navigating to cart page");
        CartPage cartPage = catalog.goToCartPage();

        Assert.assertTrue(
                cartPage.isAt(),
                "Cart page failed to load after navigation"
        );

        return cartPage;
    }
}