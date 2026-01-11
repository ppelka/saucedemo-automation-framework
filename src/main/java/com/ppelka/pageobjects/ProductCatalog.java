package com.ppelka.pageobjects;

import com.ppelka.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object representing the product catalog (inventory) page.
 * Provides product lookup, add-to-cart actions, and navigation to the cart.
 */
public class ProductCatalog extends AbstractComponent {

    // ============================================================
    // Locators
    // ============================================================
    private final By productCard        = By.cssSelector(".inventory_item");
    private final By productName        = By.cssSelector(".inventory_item_name");
    private final By addToCartButton    = By.cssSelector("button.btn_primary.btn_small");
    private final By removeButton       = By.cssSelector("button.btn_secondary.btn_small");
    private final By cartBadge          = By.className("shopping_cart_badge");
    private final By shoppingCartLink   = By.cssSelector("a.shopping_cart_link");

    // ============================================================
    // Constructor
    // ============================================================
    public ProductCatalog(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    /**
     * Confirms that the current page is the product catalog.
     */
    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("inventory");
    }

    /**
     * Waits until the product catalog is fully loaded.
     */
    public void waitForPageToLoad() {
        waitForVisible(productCard);
    }

    // ============================================================
    // Helpers
    // ============================================================

    /**
     * Finds a product card by its displayed name.
     * Throws an exception if the product is not found.
     */
    private WebElement getProductCard(String name) {
        waitForPageToLoad();
        return findAll(productCard).stream()
                .filter(card -> card.findElement(productName).getText().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + name));
    }

    // ============================================================
    // Actions
    // ============================================================

    /**
     * Adds a product to the cart by its name.
     * Waits until the "Remove" button becomes visible.
     */
    public void addProduct(String name) {
        WebElement card = getProductCard(name);
        card.findElement(addToCartButton).click();
        waitForVisible(card.findElement(removeButton));
    }

    /**
     * Checks whether the "Remove" button is visible for a given product.
     * Used to confirm that the product was successfully added to the cart.
     */
    public boolean isRemoveButtonVisible(String name) {
        WebElement card = getProductCard(name);
        return card.findElement(removeButton).isDisplayed();
    }

    /**
     * Returns the number displayed in the cart badge.
     * Returns "0" if the badge is not present.
     */
    public String getCartQuantity() {
        if (findAll(cartBadge).isEmpty()) {
            return "0";
        }
        return find(cartBadge).getText();
    }

    /**
     * Navigates to the cart page and waits until it is fully loaded.
     */
    public CartPage goToCartPage() {
        find(shoppingCartLink).click();
        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageToLoad();
        return cartPage;
    }
}
