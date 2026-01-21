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

    /**
     * Confirms that the current page is the checkout complete page.
     */
    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }

    /**
     * Waits until the checkout complete page is fully loaded.
     */
    public void waitForPageToLoad() {
        waitForVisible(completeHeader);
    }

    // ============================================================
    // Verification
    // ============================================================

    /**
     * Returns true if the order success message is visible.
     */
    public boolean isOrderSuccessMessageVisible() {
        return find(completeHeader).isDisplayed();
    }
}
