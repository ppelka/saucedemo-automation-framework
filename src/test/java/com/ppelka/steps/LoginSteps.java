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

    /**
     * Opens the login page and verifies that it is displayed.
     */
    @Step("Open login page")
    public LoginSteps openLoginPage() {
        log.info("Opening login page");
        loginPage.goTo();
        Assert.assertTrue(loginPage.isAt(), "Login page is not displayed");
        return this;
    }

    /**
     * Performs a valid login and verifies that the user is redirected
     * to the product catalog page.
     */
    @Step("Log in with valid credentials: {username}")
    public ProductCatalog loginValid(String username, String password) {
        log.info("Logging in with valid credentials: {}", username);
        ProductCatalog catalog = loginPage.loginValid(username, password);
        Assert.assertTrue(catalog.isAt(), "User was not redirected to product catalog");
        return catalog;
    }

    /**
     * Attempts to log in with invalid credentials.
     */
    @Step("Attempt login with invalid credentials: {username}")
    public LoginSteps loginInvalid(String username, String password) {
        log.info("Attempting invalid login with username: {}", username);
        loginPage.loginInvalid(username, password);
        return this;
    }

    /**
     * Verifies that the login error message contains the expected text.
     */
    @Step("Verify login error message contains: \"{expectedMessage}\"")
    public LoginSteps verifyLoginError(String expectedMessage) {
        String actual = loginPage.getLoginErrorMessage();
        log.info("Verifying login error message. Expected to contain: '{}'. Actual: '{}'",
                expectedMessage, actual);

        Assert.assertTrue(
                actual.contains(expectedMessage),
                "Expected error message to contain: " + expectedMessage + "\nActual: " + actual
        );
        return this;
    }
}
