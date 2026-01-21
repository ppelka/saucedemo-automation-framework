package com.ppelka.steps;

import com.ppelka.pageobjects.CheckoutCompletePage;
import com.ppelka.pageobjects.CheckoutOverviewPage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step definitions for checkout overview actions.
 * Provides high-level business steps used in test scenarios.
 */
public class CheckoutOverviewSteps {

    private static final Logger log = LoggerFactory.getLogger(CheckoutOverviewSteps.class);

    private final CheckoutOverviewPage overviewPage;

    public CheckoutOverviewSteps(CheckoutOverviewPage overviewPage) {
        this.overviewPage = overviewPage;
    }

    /**
     * Verifies that a product is visible in the checkout overview.
     */
    @Step("Verify product visible in overview: {productName}")
    public CheckoutOverviewSteps verifyProductVisible(String productName) {
        log.info("Verifying product visible in overview: {}", productName);
        Assert.assertTrue(
                overviewPage.isProductVisible(productName),
                "Product not visible in overview: " + productName
        );
        return this;
    }

    /**
     * Completes the checkout process.
     */
    @Step("Finish checkout")
    public CheckoutCompletePage finishCheckout() {
        log.info("Finishing checkout");
        return overviewPage.finishCheckout();
    }
}
