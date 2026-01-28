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
    private final By cartItem        = By.cssSelector(".cart_item");
    private final By itemName        = By.cssSelector(".inventory_item_name");
    private final By removeButton    = By.cssSelector("button.cart_button");
    private final By checkoutButton  = By.id("checkout");

    // ============================================================
    // Constructor
    // ============================================================
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("cart")
                && !findAll(cartItem).isEmpty();
    }

    public void waitForPageToLoad() {
        waitForUrlContains("cart");
        waitForVisible(cartItem);
    }

    // ============================================================
    // Actions
    // ============================================================

    public boolean isProductVisible(String name) {
        return findAll(cartItem).stream()
                .anyMatch(i -> i.findElement(itemName).getText().equalsIgnoreCase(name));
    }

    public void removeProduct(String name) {
        WebElement item = findAll(cartItem).stream()
                .filter(i -> i.findElement(itemName).getText().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart: " + name));

        WebElement removeBtn = item.findElement(removeButton);

        try {
            removeBtn.click();
        } catch (Exception e) {
            jsClick(removeBtn);
        }

        waitForTextToDisappear(itemName, name);
    }

    public boolean isCartEmpty() {
        return findAll(cartItem).isEmpty();
    }

    public CheckoutInformationPage proceedToCheckout() {
        click(checkoutButton);
        CheckoutInformationPage infoPage = new CheckoutInformationPage(driver);
        infoPage.waitForPageToLoad();
        return infoPage;
    }
}