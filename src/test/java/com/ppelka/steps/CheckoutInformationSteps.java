package com.ppelka.steps;

import com.ppelka.pageobjects.CheckoutInformationPage;
import com.ppelka.pageobjects.CheckoutOverviewPage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Step definitions for checkout information actions.
 * Provides high-level business steps used in test scenarios.
 */
public class CheckoutInformationSteps {

    private static final Logger log = LoggerFactory.getLogger(CheckoutInformationSteps.class);

    private final CheckoutInformationPage infoPage;

    public CheckoutInformationSteps(CheckoutInformationPage infoPage) {
        this.infoPage = infoPage;
    }

    /**
     * Enters customer information.
     */
    @Step("Enter customer information: {firstName} {lastName}, {postalCode}")
    public CheckoutInformationSteps enterCustomerInfo(String firstName, String lastName, String postalCode) {
        log.info("Entering customer info: {} {} {}", firstName, lastName, postalCode);
        infoPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postalCode);
        return this;
    }

    /**
     * Proceeds to the checkout overview page.
     */
    @Step("Continue to checkout overview page")
    public CheckoutOverviewPage continueToOverview() {
        log.info("Continuing to checkout overview page");
        return infoPage.continueToOverview();
    }
}
