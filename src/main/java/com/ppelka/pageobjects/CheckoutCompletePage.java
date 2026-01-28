package com.ppelka.pageobjects;

import com.ppelka.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the final confirmation page after purchase.
 * Provides verification of successful order completion.
 */
public class CheckoutCompletePage extends AbstractComponent {

    // ============================================================
    // Locators
    // ============================================================
    private final By completeHeader = By.cssSelector(".complete-header");

    // ============================================================
    // Constructor
    // ============================================================
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("checkout-complete")
                && !findAll(completeHeader).isEmpty();
    }

    public void waitForPageToLoad() {
        waitForVisible(completeHeader);
    }

    // ============================================================
    // Verification
    // ============================================================

    public boolean isOrderSuccessMessageVisible() {
        return find(completeHeader).isDisplayed();
    }
}