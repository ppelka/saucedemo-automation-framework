package com.ppelka.core;

import org.openqa.selenium.WebDriver;

/**
 * Thread-safe WebDriver manager.
 *
 * Stores a separate WebDriver instance per thread using ThreadLocal,
 * enabling parallel test execution without driver collisions.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Returns the WebDriver instance associated with the current thread.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Assigns a WebDriver instance to the current thread.
     */
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    /**
     * Quits and removes the WebDriver instance for the current thread.
     * Ensures proper cleanup during parallel execution.
     */
    public static void quitDriver() {
        WebDriver instance = driver.get();
        if (instance != null) {
            instance.quit();
            driver.remove();
        }
    }
}
