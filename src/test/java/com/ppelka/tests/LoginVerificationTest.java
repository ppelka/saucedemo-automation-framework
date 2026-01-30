package com.ppelka.tests;

import com.ppelka.testbase.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Parameterized login test demonstrating:
 *  - Successful login for valid users
 *  - Error handling for invalid or restricted users
 *  - Allure reporting for each data-driven iteration
 */
@Epic("Authentication")
@Feature("Login")
public class LoginVerificationTest extends BaseTest {

    @DataProvider(name = "loginUsers")
    public Object[][] loginUsers() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true,  "Valid user should log in successfully"},
                {"locked_out_user", "secret_sauce", false, "Locked out user should see an error"},
                {"problem_user", "secret_sauce", true,  "Problem user should still log in"},
                {"wrong_user", "wrong_pass", false, "Invalid credentials should show error"}
        };
    }

    @Test(
            dataProvider = "loginUsers",
            description = "Parameterized login test for multiple user types",
            groups = {"regression"}
    )
    @Story("User attempts to log in with various credential types")
    @Severity(SeverityLevel.BLOCKER)
    public void loginTest(String username, String password, boolean shouldSucceed, String description) {

        loginSteps.openLoginPage();

        if (shouldSucceed) {
            loginSteps.loginValid(username, password);
        } else {
            loginSteps.loginInvalid(username, password)
                    .verifyLoginError("Epic sadface");
        }
    }
}