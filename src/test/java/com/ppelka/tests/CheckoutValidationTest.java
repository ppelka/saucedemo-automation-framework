package com.ppelka.tests;

import com.ppelka.testbase.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Checkout")
@Feature("Validation")
public class CheckoutValidationTest extends BaseTest {

    @Test(
            description = "User cannot proceed to checkout overview without filling required customer info",
            groups = {"regression"}
    )
    @Story("User cannot proceed without required customer info")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldShowErrorWhenCustomerInfoIsMissing() {

        loginSteps.openLoginPage()
                .loginValid("standard_user", "secret_sauce");

        productSteps.addProduct("Sauce Labs Backpack")
                .goToCart();

        cartSteps.proceedToCheckout();

        infoSteps.clickContinueWithoutInfo()
                .verifyErrorMessage("Error: First Name is required");
    }
}