package com.ppelka.core;

import org.openqa.selenium.WebDriver;

/**
 * Thread-safe WebDriver manager using ThreadLocal.
 * Ensures each test thread receives its own isolated WebDriver instance.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
        // Prevent instantiation
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static boolean hasDriver() {
        return driver.get() != null;
    }

    public static void quitDriver() {
        WebDriver instance = driver.get();
        if (instance != null) {
            instance.quit();
            driver.remove();
        }
    }
}
