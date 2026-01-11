package com.ppelka.tests;

import com.ppelka.pageobjects.ProductCatalog;
import org.testng.annotations.Test;
import com.ppelka.testbase.BaseTest;

/**
 * Test suite verifying login functionality:
 *  - Valid login redirects to the product catalog
 *  - Invalid login displays an appropriate error message
 */
public class LoginVerificationTest extends BaseTest {

    @Test(description = "Valid login should redirect to product catalog")
    public void validLoginTest() {

        // Login with valid credentials
        ProductCatalog catalog = loginSteps
                .openLoginPage()
                .loginValid("standard_user", "secret_sauce");

        // Assertion is handled inside LoginSteps → test remains clean
    }

    @Test(description = "Invalid login should show error message")
    public void invalidLoginTest() {

        // Attempt login with invalid credentials and verify error message
        loginSteps
                .openLoginPage()
                .loginInvalid("wrong_user", "wrong_pass")
                .verifyLoginError("Epic sadface");
    }
}
