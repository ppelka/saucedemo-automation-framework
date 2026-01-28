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
    private final By productCard      = By.cssSelector(".inventory_item");
    private final By productName      = By.cssSelector(".inventory_item_name");
    private final By addToCartButton  = By.cssSelector("button.btn_primary.btn_small");
    private final By removeButton     = By.cssSelector("button.btn_secondary.btn_small");
    private final By cartBadge        = By.className("shopping_cart_badge");
    private final By shoppingCartLink = By.cssSelector("a.shopping_cart_link");

    // ============================================================
    // Constructor
    // ============================================================
    public ProductCatalog(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("inventory")
                && !findAll(productCard).isEmpty();
    }

    public void waitForPageToLoad() {
        waitForVisible(productCard);
    }

    // ============================================================
    // Helpers
    // ============================================================

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

    public void addProduct(String name) {
        WebElement card = getProductCard(name);
        WebElement addBtn = card.findElement(addToCartButton);

        try {
            addBtn.click();
        } catch (Exception e) {
            // fallback to JS click if intercepted
            jsClick(addBtn);
        }

        waitForVisible(card.findElement(removeButton));
    }

    public boolean isRemoveButtonVisible(String name) {
        WebElement card = getProductCard(name);
        return card.findElement(removeButton).isDisplayed();
    }

    public String getCartQuantity() {
        if (findAll(cartBadge).isEmpty()) {
            return "0";
        }
        return getText(cartBadge);
    }

    public CartPage goToCartPage() {
        click(shoppingCartLink);
        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageToLoad();
        return cartPage;
    }
}