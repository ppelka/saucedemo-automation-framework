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

public final class WebDriverFactory {

    private WebDriverFactory() {}

    public enum BrowserType {
        CHROME, FIREFOX, EDGE;

        public static BrowserType from(String value) {
            if (value == null || value.isBlank()) return CHROME;
            switch (value.trim().toLowerCase()) {
                case "firefox":
                case "ff":
                    return FIREFOX;
                case "edge":
                    return EDGE;
                default:
                    return CHROME;
            }
        }
    }

    public static WebDriver createDriver(String browserName) {

        BrowserType type = BrowserType.from(browserName);
        boolean headless = ConfigReader.getBoolean("headless", false);

        switch (type) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return firefox(headless);

            case EDGE:
                WebDriverManager.edgedriver().setup();
                return edge(headless);

            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                return chrome(headless);
        }
    }

    private static WebDriver chrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        if (headless) options.addArguments("--headless=new");

        options.addArguments(
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-popup-blocking",
                "--disable-notifications",
                "--disable-extensions",
                "--window-size=1920,1080"
        );

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        return new ChromeDriver(options);
    }

    private static WebDriver firefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        options.addArguments("--width=1920", "--height=1080");
        return new FirefoxDriver(options);
    }

    private static WebDriver edge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        return new EdgeDriver(options);
    }
}
