package com.ppelka.steps;

import com.ppelka.pageobjects.CheckoutCompletePage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step definitions for checkout completion actions.
 * Provides high-level business steps used in test scenarios.
 */
public class CheckoutCompleteSteps {

    private static final Logger log = LoggerFactory.getLogger(CheckoutCompleteSteps.class);

    private final CheckoutCompletePage completePage;

    public CheckoutCompleteSteps(CheckoutCompletePage completePage) {
        this.completePage = completePage;
    }

    /**
     * Verifies that the order success message is visible.
     */
    @Step("Verify order success message is visible")
    public CheckoutCompleteSteps verifyOrderSuccess() {
        log.info("Verifying order success message");
        Assert.assertTrue(
                completePage.isOrderSuccessMessageVisible(),
                "Order success message not visible"
        );
        return this;
    }
}
