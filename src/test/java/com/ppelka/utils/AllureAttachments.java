package com.ppelka.utils;

import com.ppelka.core.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Utility class providing Allure attachments for debugging failed tests.
 * Includes screenshot, current URL, and page source capture.
 */
public class AllureAttachments {

    /**
     * Captures a screenshot and attaches it to the Allure report.
     * Returns an empty byte array if screenshots are not supported.
     */
    @Attachment(value = "Failure screenshot", type = "image/png")
    public static byte[] attachScreenshot() {
        if (DriverManager.getDriver() instanceof TakesScreenshot ts) {
            return ts.getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    /**
     * Attaches the current page URL to the Allure report.
     * Returns a fallback message if the URL cannot be retrieved.
     */
    @Attachment(value = "Current URL", type = "text/plain")
    public static String attachCurrentUrl() {
        try {
            return DriverManager.getDriver().getCurrentUrl();
        } catch (Exception e) {
            return "URL unavailable: " + e.getMessage();
        }
    }

    /**
     * Attaches the current page source to the Allure report.
     * Returns a fallback message if the page source cannot be retrieved.
     */
    @Attachment(value = "Page source", type = "text/html")
    public static String attachPageSource() {
        try {
            return DriverManager.getDriver().getPageSource();
        } catch (Exception e) {
            return "Page source unavailable: " + e.getMessage();
        }
    }
}
