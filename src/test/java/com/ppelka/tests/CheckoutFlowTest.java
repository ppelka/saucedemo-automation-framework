package com.ppelka.tests;

import com.ppelka.testbase.BaseTest;
import org.testng.annotations.Test;

/**
 * End-to-end checkout flow:
 *  1. Login
 *  2. Add two products to cart
 *  3. Verify cart contents
 *  4. Proceed to checkout
 *  5. Fill customer information
 *  6. Verify overview
 *  7. Complete purchase
 */
public class CheckoutFlowTest extends BaseTest {

    @Test(description = "Full purchase flow with two products")
    public void checkoutFlowTest() {

        loginSteps.openLoginPage()
                .loginValid("standard_user", "secret_sauce");

        productSteps.addProduct("Sauce Labs Backpack")
                .addProduct("Sauce Labs Bike Light")
                .verifyCartQuantity("2")
                .goToCart();

        cartSteps.verifyProductVisible("Sauce Labs Backpack")
                .verifyProductVisible("Sauce Labs Bike Light")
                .proceedToCheckout();

        infoSteps.enterCustomerInfo("Patryk", "Tester", "82-300")
                .continueToOverview();

        overviewSteps.verifyProductVisible("Sauce Labs Backpack")
                .verifyProductVisible("Sauce Labs Bike Light")
                .finishCheckout();

        completeSteps.verifyOrderSuccess();
    }
}