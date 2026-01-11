package com.ppelka.abstractcomponents;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all Page Objects.
 * Provides common WebDriver utilities, explicit wait helpers,
 * and a consistent interaction layer for UI components.
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

    /**
     * Finds a single element and waits until it is present in the DOM.
     */
    protected WebElement find(By locator) {
        log.debug("Finding element: {}", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Finds all matching elements without waiting.
     */
    protected List<WebElement> findAll(By locator) {
        log.debug("Finding all elements: {}", locator);
        return driver.findElements(locator);
    }

    // ============================================================
    // Explicit wait utilities
    // ============================================================

    /**
     * Waits until the element located by the given locator becomes visible.
     */
    protected WebElement waitForVisible(By locator) {
        log.debug("Waiting for visibility of: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the given WebElement becomes visible.
     */
    protected WebElement waitForVisible(WebElement element) {
        log.debug("Waiting for visibility of WebElement: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the element becomes clickable.
     */
    protected WebElement waitForClickable(By locator) {
        log.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the element located by the given locator becomes invisible.
     */
    protected void waitForInvisibility(By locator) {
        log.debug("Waiting for invisibility of: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until the text of the element changes or the element disappears.
     */
    protected void waitForTextToDisappear(By locator, String text) {
        log.debug("Waiting for text '{}' to disappear from element: {}", text, locator);
        wait.until(driver -> {
            try {
                String current = driver.findElement(locator).getText();
                return !current.equals(text);
            } catch (NoSuchElementException ignored) {
                return true; // element removed from DOM
            }
        });
    }

    /**
     * Waits until the current URL contains the given fragment.
     */
    protected void waitForUrlContains(String fragment) {
        log.debug("Waiting for URL to contain: {}", fragment);
        wait.until(ExpectedConditions.urlContains(fragment));
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
