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
    private final By firstNameField = By.id("first-name");
    private final By lastNameField  = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // ============================================================
    // Constructor
    // ============================================================
    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    /**
     * Confirms that the current page is the checkout information page.
     */
    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("checkout-step-one");
    }

    /**
     * Waits until the checkout information page is fully loaded.
     */
    public void waitForPageToLoad() {
        waitForVisible(firstNameField);
    }

    // ============================================================
    // Actions
    // ============================================================

    /**
     * Enters the customer's first name.
     */
    public CheckoutInformationPage enterFirstName(String firstName) {
        find(firstNameField).clear();
        find(firstNameField).sendKeys(firstName);
        return this;
    }

    /**
     * Enters the customer's last name.
     */
    public CheckoutInformationPage enterLastName(String lastName) {
        find(lastNameField).clear();
        find(lastNameField).sendKeys(lastName);
        return this;
    }

    /**
     * Enters the customer's postal code.
     */
    public CheckoutInformationPage enterPostalCode(String postalCode) {
        find(postalCodeField).clear();
        find(postalCodeField).sendKeys(postalCode);
        return this;
    }

    /**
     * Proceeds to the checkout overview page.
     */
    public CheckoutOverviewPage continueToOverview() {
        find(continueButton).click();
        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
        overviewPage.waitForPageToLoad();
        return overviewPage;
    }
}
