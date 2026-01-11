package com.ppelka.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

/**
 * Central WebDriver factory.
 *
 * Responsibilities:
 *  - Normalizes browser names
 *  - Applies consistent configuration across browsers
 *  - Supports headless mode (useful for CI/CD)
 *  - Provides stable, hardened browser options for automation
 */
public final class WebDriverFactory {

    private WebDriverFactory() {
        // Utility class; prevent instantiation
    }

    /**
     * Creates a WebDriver instance based on the provided browser name.
     * Falls back to Chrome if the name is missing or invalid.
     */
    public static WebDriver createDriver(String browserName) {

        // Normalize browser name
        String browser = (browserName == null || browserName.isBlank())
                ? "chrome"
                : browserName.trim().toLowerCase();

        // Headless mode (default: false)
        boolean headless = ConfigReader.getBoolean("headless", false);

        switch (browser) {

            case "firefox":
            case "ff":
                WebDriverManager.firefoxdriver().setup();
                return createFirefoxDriver(headless);

            case "edge":
                WebDriverManager.edgedriver().setup();
                return createEdgeDriver(headless);

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return createChromeDriver(headless);
        }
    }

    // ============================================================
    // Chrome
    // ============================================================

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        // Stable automation flags (recommended for CI/CD)
        options.addArguments(
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-popup-blocking",
                "--disable-notifications",
                "--disable-extensions",
                "--disable-features=PasswordCheck",
                "--disable-features=PasswordLeakDetection",
                "--disable-features=SafetyCheck",
                "--disable-features=SafeBrowsingEnhancedProtection",
                "--disable-features=SafeBrowsingProtection",
                "--disable-features=OptimizationGuideModelDownloading",
                "--window-size=1920,1080"
        );

        // Disable password manager and safe browsing prompts
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "safebrowsing.enabled", false
        ));

        return new ChromeDriver(options);
    }

    // ============================================================
    // Firefox
    // ============================================================

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
        }

        options.addArguments("--width=1920", "--height=1080");

        return new FirefoxDriver(options);
    }

    // ============================================================
    // Edge
    // ============================================================

    private static WebDriver createEdgeDriver(boolean headless) {
        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments(
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--window-size=1920,1080"
        );

        return new EdgeDriver(options);
    }
}
