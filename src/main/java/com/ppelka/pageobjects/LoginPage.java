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

    /**
     * Base URL for the application.
     * Can be overridden via -Dbase.url system property.
     */
    private final String BASE_URL = System.getProperty("base.url", "https://www.saucedemo.com/");

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

    /**
     * Confirms that the current page is the login page.
     */
    @Override
    public boolean isAt() {
        return driver.getCurrentUrl().contains("saucedemo.com");
    }

    /**
     * Waits until the login page is fully loaded.
     */
    public void waitForPageToLoad() {
        waitForVisible(usernameField);
    }

    // ============================================================
    // Actions
    // ============================================================

    /**
     * Navigates to the login page and waits until it is ready.
     */
    public void goTo() {
        driver.get(BASE_URL);
        waitForPageToLoad();
    }

    /**
     * Performs a valid login and returns the ProductCatalog page object.
     */
    public ProductCatalog loginValid(String username, String password) {
        find(usernameField).clear();
        find(passwordField).clear();

        find(usernameField).sendKeys(username);
        find(passwordField).sendKeys(password);
        find(loginButton).click();

        // Wait until redirected to the inventory page
        waitForVisible(inventoryItem);
        return new ProductCatalog(driver);
    }

    /**
     * Attempts to log in with invalid credentials.
     * Waits until the error message becomes visible.
     */
    public void loginInvalid(String username, String password) {
        find(usernameField).clear();
        find(passwordField).clear();

        find(usernameField).sendKeys(username);
        find(passwordField).sendKeys(password);
        find(loginButton).click();

        waitForVisible(loginError);
    }

    /**
     * Returns the text of the login error message.
     */
    public String getLoginErrorMessage() {
        return find(loginError).getText();
    }
}
