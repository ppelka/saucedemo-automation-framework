package com.ppelka.pageobjects;

import com.ppelka.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the checkout information page.
 * Provides customer data entry and navigation to the overview page.
 */
public class CheckoutInformationPage extends AbstractComponent {

    // ============================================================
    // Locators
    // ============================================================
    private final By firstNameField   = By.id("first-name");
    private final By lastNameField    = By.id("last-name");
    private final By postalCodeField  = By.id("postal-code");
    private final By continueButton   = By.id("continue");

    // ============================================================
    // Constructor
    // ============================================================
    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("checkout-step-one")
                && !findAll(firstNameField).isEmpty();
    }

    public void waitForPageToLoad() {
        waitForVisible(firstNameField);
    }

    // ============================================================
    // Actions
    // ============================================================

    public CheckoutInformationPage enterFirstName(String firstName) {
        type(firstNameField, firstName);
        return this;
    }

    public CheckoutInformationPage enterLastName(String lastName) {
        type(lastNameField, lastName);
        return this;
    }

    public CheckoutInformationPage enterPostalCode(String postalCode) {
        type(postalCodeField, postalCode);
        return this;
    }

    public CheckoutOverviewPage continueToOverview() {
        click(continueButton);
        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
        overviewPage.waitForPageToLoad();
        return overviewPage;
    }
}