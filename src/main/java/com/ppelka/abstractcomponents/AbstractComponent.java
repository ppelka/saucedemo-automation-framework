package com.ppelka.abstractcomponents;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all Page Objects and reusable UI components.
 * Provides:
 *  - Stable element lookup
 *  - Explicit wait utilities
 *  - Safe interaction helpers (click, type, getText)
 *  - Page identity contract (isAt)
 *
 * Ensures consistent and reliable interactions across the entire framework.
 */
public abstract class AbstractComponent {

    private static final Logger log = LoggerFactory.getLogger(AbstractComponent.class);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected AbstractComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ============================================================
    // Element lookup helpers
    // ============================================================

    protected WebElement find(By locator) {
        log.debug("Finding element: {}", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> findAll(By locator) {
        log.debug("Finding all elements: {}", locator);
        return driver.findElements(locator);
    }

    // ============================================================
    // Explicit wait utilities
    // ============================================================

    protected WebElement waitForVisible(By locator) {
        log.debug("Waiting for visibility of: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisible(WebElement element) {
        log.debug("Waiting for visibility of WebElement: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickable(By locator) {
        log.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForInvisibility(By locator) {
        log.debug("Waiting for invisibility of: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForTextToDisappear(By locator, String text) {
        log.debug("Waiting for text '{}' to disappear from: {}", text, locator);
        wait.until(driver -> {
            try {
                String current = driver.findElement(locator).getText();
                return !current.equals(text);
            } catch (NoSuchElementException ignored) {
                return true;
            }
        });
    }

    protected void waitForUrlContains(String fragment) {
        log.debug("Waiting for URL to contain: {}", fragment);
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    // ============================================================
    // Safe interaction helpers
    // ============================================================

    protected void click(By locator) {
        log.debug("Clicking element: {}", locator);
        WebElement element = waitForClickable(locator);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted, retrying with JS: {}", locator);
            jsClick(element);
        }
    }

    protected void type(By locator, String text) {
        log.debug("Typing '{}' into element: {}", text, locator);
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        log.debug("Getting text from: {}", locator);
        return waitForVisible(locator).getText();
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ============================================================
    // Page identity contract
    // ============================================================

    /**
     * Ensures that the current page/component is loaded and ready.
     * Each Page Object must implement its own identity check.
     */
    public abstract boolean isAt();
}
