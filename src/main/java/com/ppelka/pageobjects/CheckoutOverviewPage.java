package com.ppelka.pageobjects;

import com.ppelka.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the checkout overview page.
 * Displays selected products and allows finishing the order.
 */
public class CheckoutOverviewPage extends AbstractComponent {

    // ============================================================
    // Locators
    // ============================================================
    private final By overviewItem = By.cssSelector(".cart_item");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By finishButton = By.id("finish");

    // ============================================================
    // Constructor
    // ============================================================
    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    /**
     * Confirms that the current page is the checkout overview page.
     */
    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("checkout-step-two");
    }

    /**
     * Waits until the checkout overview page is fully loaded.
     */
    public void waitForPageToLoad() {
        waitForVisible(overviewItem);
    }

    // ============================================================
    // Actions
    // ============================================================

    /**
     * Checks whether a product with the given name is visible in the overview.
     */
    public boolean isProductVisible(String name) {
        return findAll(overviewItem).stream()
                .anyMatch(i -> i.findElement(itemName).getText().equalsIgnoreCase(name));
    }

    /**
     * Completes the checkout process and navigates to the final confirmation page.
     */
    public CheckoutCompletePage finishCheckout() {
        find(finishButton).click();
        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        completePage.waitForPageToLoad();
        return completePage;
    }
}
