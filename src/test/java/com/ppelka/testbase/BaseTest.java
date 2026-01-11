package com.ppelka.testbase;

import com.ppelka.pageobjects.LoginPage;
import com.ppelka.steps.LoginSteps;
import com.ppelka.steps.ProductCatalogSteps;
import com.ppelka.steps.CartSteps;
import com.ppelka.core.ConfigReader;
import com.ppelka.core.DriverManager;
import com.ppelka.core.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Base class for all UI tests.
 * Handles WebDriver initialization, configuration, cleanup,
 * and provides access to common Page Objects and Step classes.
 */
public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    // Page Objects
    protected LoginPage loginPage;

    // Steps
    protected LoginSteps loginSteps;
    protected ProductCatalogSteps productSteps;
    protected CartSteps cartSteps;

    /**
     * Initializes WebDriver, loads configuration, and prepares Page Objects and Steps.
     * Browser selection priority:
     *  1. -Dbrowser system property
     *  2. <parameter name="browser"> from testng.xml
     *  3. config.properties
     *  4. Default: chrome
     */
    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browserFromTestNG) {

        log.info("=== START TEST SETUP ===");

        String browser =
                System.getProperty("browser") != null
                        ? System.getProperty("browser")
                        : (browserFromTestNG != null
                        ? browserFromTestNG
                        : ConfigReader.get("browser", "chrome"));

        log.info("Selected browser: {}", browser);

        driver = WebDriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        // Initialize Page Objects
        loginPage = new LoginPage(driver);

        // Initialize Steps
        loginSteps = new LoginSteps(loginPage);

        // Navigate to base URL
        String url = ConfigReader.get("base.url");
        log.info("Navigating to base URL: {}", url);
        driver.get(url);

        log.info("=== SETUP COMPLETE ===");
    }

    /**
     * Handles test cleanup, attaches debugging artifacts to Allure,
     * and ensures WebDriver is properly closed.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        log.info("=== START TEST TEARDOWN ===");

        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                log.error("Test FAILED: {}", result.getMethod().getMethodName());
                AllureAttachments.attachScreenshot();
                AllureAttachments.attachPageSource();
                AllureAttachments.attachCurrentUrl();
            } else {
                log.info("Test PASSED: {}", result.getMethod().getMethodName());
            }
        } finally {
            log.info("Closing WebDriver");
            DriverManager.quitDriver();
            log.info("=== TEARDOWN COMPLETE ===");
        }
    }
}
