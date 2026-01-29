package com.ppelka.steps;

import com.ppelka.pageobjects.LoginPage;
import com.ppelka.pageobjects.ProductCatalog;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step definitions for login-related actions.
 * Provides high-level business steps used in test scenarios.
 */
public class LoginSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);

    private final LoginPage loginPage;

    public LoginSteps(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    // ============================================================
    // Navigation
    // ============================================================

    @Step("Open login page")
    public LoginSteps openLoginPage() {
        log.info("Opening login page");
        loginPage.goTo();
        Assert.assertTrue(loginPage.isAt(), "Login page failed to load");
        return this;
    }

    // ============================================================
    // Valid login
    // ============================================================

    @Step("Log in with valid credentials: {username}")
    public ProductCatalog loginValid(String username, String password) {
        log.info("Logging in with valid credentials: {}", username);

        ProductCatalog catalog = loginPage.loginValid(username, password);

        Assert.assertTrue(
                catalog.isAt(),
                "User was not redirected to the product catalog after valid login"
        );

        return catalog;
    }

    // ============================================================
    // Invalid login
    // ============================================================

    @Step("Attempt login with invalid credentials: {username}")
    public LoginSteps loginInvalid(String username, String password) {
        log.info("Attempting invalid login with username: {}", username);

        loginPage.loginInvalid(username, password);

        Assert.assertTrue(
                loginPage.isAt(),
                "Login page should remain visible after invalid login attempt"
        );

        return this;
    }

    @Step("Verify login error message contains: \"{expectedMessage}\"")
    public LoginSteps verifyLoginError(String expectedMessage) {
        String actual = loginPage.getLoginErrorMessage();

        log.info(
                "Verifying login error message. Expected to contain: '{}'. Actual: '{}'",
                expectedMessage, actual
        );

        Assert.assertTrue(
                actual.contains(expectedMessage),
                "Expected error message to contain: " + expectedMessage + "\nActual: " + actual
        );

        return this;
    }
}