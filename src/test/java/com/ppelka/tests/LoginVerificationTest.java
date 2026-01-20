package com.ppelka.tests;

import com.ppelka.pageobjects.ProductCatalog;
import com.ppelka.testbase.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Parameterized login test demonstrating:
 *  - Successful login for valid users
 *  - Error handling for invalid or restricted users
 *  - Allure reporting for each data-driven iteration
 */
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
            description = "Parameterized login test for multiple user types"
    )
    public void loginTest(String username, String password, boolean shouldSucceed, String description) {

        loginSteps.openLoginPage();

        if (shouldSucceed) {
            ProductCatalog catalog = loginSteps.loginValid(username, password);
        } else {
            loginSteps
                    .loginInvalid(username, password)
                    .verifyLoginError("Epic sadface");
        }
    }
}
