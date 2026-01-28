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
    private final By overviewItem   = By.cssSelector(".cart_item");
    private final By itemName       = By.cssSelector(".inventory_item_name");
    private final By finishButton   = By.id("finish");

    // ============================================================
    // Constructor
    // ============================================================
    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("checkout-step-two")
                && !findAll(overviewItem).isEmpty();
    }

    public void waitForPageToLoad() {
        waitForVisible(overviewItem);
    }

    // ============================================================
    // Actions
    // ============================================================

    public boolean isProductVisible(String name) {
        return findAll(overviewItem).stream()
                .anyMatch(i -> i.findElement(itemName).getText().equalsIgnoreCase(name));
    }

    public CheckoutCompletePage finishCheckout() {
        click(finishButton);
        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        completePage.waitForPageToLoad();
        return completePage;
    }
}