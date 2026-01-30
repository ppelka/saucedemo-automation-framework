package com.ppelka.tests;

import com.ppelka.testbase.BaseTest;
import org.testng.annotations.Test;

/**
 * End-to-end test verifying that a product can be added to the cart
 * and successfully removed afterward.
 */
public class AddProductToCartAndRemoveItTest extends BaseTest {

    @Test(
            description = "Add product to cart and remove it",
            groups = {"smoke", "regression"}
    )
    public void addAndRemoveProductTest() {

        loginSteps.openLoginPage()
                .loginValid("standard_user", "secret_sauce");

        productSteps.addProduct("Sauce Labs Backpack")
                .verifyCartQuantity("1")
                .goToCart();

        cartSteps.verifyProductVisible("Sauce Labs Backpack")
                .removeProduct("Sauce Labs Backpack")
                .verifyCartIsEmpty();
    }
}