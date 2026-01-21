package com.ppelka.tests;

import com.ppelka.pageobjects.CartPage;
import com.ppelka.pageobjects.CheckoutCompletePage;
import com.ppelka.pageobjects.CheckoutInformationPage;
import com.ppelka.pageobjects.CheckoutOverviewPage;
import com.ppelka.pageobjects.ProductCatalog;
import com.ppelka.steps.CartSteps;
import com.ppelka.steps.CheckoutCompleteSteps;
import com.ppelka.steps.CheckoutInformationSteps;
import com.ppelka.steps.CheckoutOverviewSteps;
import com.ppelka.steps.ProductCatalogSteps;
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

        // Login
        ProductCatalog catalog = loginSteps
                .openLoginPage()
                .loginValid("standard_user", "secret_sauce");

        // Add products to cart
        ProductCatalogSteps catalogSteps = new ProductCatalogSteps(catalog);

        catalogSteps
                .addProduct("Sauce Labs Backpack")
                .addProduct("Sauce Labs Bike Light")
                .verifyCartQuantity("2");

        // Go to cart
        CartPage cartPage = catalogSteps.goToCart();
        CartSteps cartSteps = new CartSteps(cartPage);

        cartSteps
                .verifyProductVisible("Sauce Labs Backpack")
                .verifyProductVisible("Sauce Labs Bike Light");

        // Proceed to checkout information
        CheckoutInformationPage infoPage = cartSteps.proceedToCheckout();
        CheckoutInformationSteps infoSteps = new CheckoutInformationSteps(infoPage);

        infoSteps.enterCustomerInfo("Patryk", "Tester", "82-300");

        // Continue to overview
        CheckoutOverviewPage overviewPage = infoSteps.continueToOverview();
        CheckoutOverviewSteps overviewSteps = new CheckoutOverviewSteps(overviewPage);

        overviewSteps
                .verifyProductVisible("Sauce Labs Backpack")
                .verifyProductVisible("Sauce Labs Bike Light");

        // Finish checkout
        CheckoutCompletePage completePage = overviewSteps.finishCheckout();
        CheckoutCompleteSteps completeSteps = new CheckoutCompleteSteps(completePage);

        completeSteps.verifyOrderSuccess();
    }
}
