package com.ppelka.pageobjects;

import com.ppelka.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the login page of SauceDemo.
 * Provides navigation, login actions, and page identity validation.
 */
public class LoginPage extends AbstractComponent {

    // ============================================================
    // Locators
    // ============================================================

    private static final String DEFAULT_URL = "https://www.saucedemo.com/";
    private final String BASE_URL = System.getProperty("base.url", DEFAULT_URL);

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By loginError    = By.cssSelector("h3[data-test='error']");
    private final By inventoryItem = By.cssSelector(".inventory_item");

    // ============================================================
    // Constructor
    // ============================================================

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ============================================================
    // Page identity
    // ============================================================

    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("saucedemo.com")
                && findAll(usernameField).size() > 0;
    }

    public void waitForPageToLoad() {
        waitForVisible(usernameField);
    }

    // ============================================================
    // Actions
    // ============================================================

    public void goTo() {
        driver.get(BASE_URL);
        waitForPageToLoad();
    }

    public ProductCatalog loginValid(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);

        waitForVisible(inventoryItem);
        return new ProductCatalog(driver);
    }

    public void loginInvalid(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);

        waitForVisible(loginError);
    }

    public String getLoginErrorMessage() {
        return getText(loginError);
    }
}
