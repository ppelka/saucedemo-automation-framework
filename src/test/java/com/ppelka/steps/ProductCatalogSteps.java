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

    /**
     * Adds a product to the cart and verifies that the action was successful.
     */
    @Step("Add product to cart: {productName}")
    public ProductCatalogSteps addProduct(String productName) {
        log.info("Adding product to cart: {}", productName);
        catalog.addProduct(productName);

        Assert.assertTrue(
                catalog.isRemoveButtonVisible(productName),
                "Remove button is not visible after adding product: " + productName
        );
        return this;
    }

    /**
     * Verifies that the cart badge displays the expected quantity.
     */
    @Step("Verify cart quantity is: {expected}")
    public ProductCatalogSteps verifyCartQuantity(String expected) {
        log.info("Verifying cart quantity. Expected: {}", expected);
        Assert.assertEquals(catalog.getCartQuantity(), expected, "Cart quantity mismatch");
        return this;
    }

    /**
     * Navigates from the product catalog to the cart page.
     */
    @Step("Navigate to cart page")
    public CartPage goToCart() {
        log.info("Navigating to cart page");
        return catalog.goToCartPage();
    }
}
