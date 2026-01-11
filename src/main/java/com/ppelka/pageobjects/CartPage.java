package com.ppelka.pageobjects;

import com.ppelka.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object representing the shopping cart page.
 * Provides product visibility checks, item removal, and page identity validation.
 */
public class CartPage extends AbstractComponent {

    // ============================================================
    // Locators
    // ============================================================
    private final By cartItem = By.cssSelector(".cart_item");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By removeButton = By.cssSelector("button.cart_button");

    // ============================================================
    // Constructor
    // ============================================================
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    /**
     * Confirms that the current page is the cart page.
     */
    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("cart");
    }

    /**
     * Waits until the cart page is fully loaded and visible.
     */
    public void waitForPageToLoad() {
        waitForUrlContains("cart");
        waitForVisible(cartItem);
    }

    // ============================================================
    // Actions
    // ============================================================

    /**
     * Checks whether a product with the given name is visible in the cart.
     */
    public boolean isProductVisible(String name) {
        return findAll(cartItem).stream()
                .anyMatch(i -> i.findElement(itemName).getText().equalsIgnoreCase(name));
    }

    /**
     * Removes a product from the cart by its name.
     * Throws an exception if the product is not found.
     */
    public void removeProduct(String name) {
        WebElement item = findAll(cartItem).stream()
                .filter(i -> i.findElement(itemName).getText().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart: " + name));

        String previousText = item.findElement(itemName).getText();
        item.findElement(removeButton).click();

        // Wait until the product name disappears from the cart
        waitForTextToDisappear(itemName, previousText);
    }

    /**
     * Returns true if the cart contains no items.
     */
    public boolean isCartEmpty() {
        return findAll(cartItem).isEmpty();
    }
}
