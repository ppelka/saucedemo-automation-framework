package com.ppelka.testbase;

import com.ppelka.core.ConfigReader;
import com.ppelka.core.DriverManager;
import com.ppelka.core.WebDriverFactory;
import com.ppelka.pageobjects.*;
import com.ppelka.steps.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Base test class providing driver setup, teardown, and shared step/page initialization.
 */
public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    // Page Objects
    protected LoginPage loginPage;
    protected ProductCatalog productCatalog;
    protected CartPage cartPage;
    protected CheckoutInformationPage infoPage;
    protected CheckoutOverviewPage overviewPage;
    protected CheckoutCompletePage completePage;

    // Steps
    protected LoginSteps loginSteps;
    protected ProductCatalogSteps productSteps;
    protected CartSteps cartSteps;
    protected CheckoutInformationSteps infoSteps;
    protected CheckoutOverviewSteps overviewSteps;
    protected CheckoutCompleteSteps completeSteps;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browserFromTestNG) {

        log.info("=== START TEST SETUP ===");

        String browser = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : (browserFromTestNG != null
                ? browserFromTestNG
                : ConfigReader.get("browser", "chrome"));

        driver = WebDriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        initPageObjects();
        initSteps();

        String url = ConfigReader.get("base.url");
        log.info("Navigating to base URL: {}", url);
        driver.get(url);

        log.info("=== SETUP COMPLETE ===");
    }

    private void initPageObjects() {
        loginPage = new LoginPage(driver);
        productCatalog = new ProductCatalog(driver);
        cartPage = new CartPage(driver);
        infoPage = new CheckoutInformationPage(driver);
        overviewPage = new CheckoutOverviewPage(driver);
        completePage = new CheckoutCompletePage(driver);
    }

    private void initSteps() {
        loginSteps = new LoginSteps(loginPage);
        productSteps = new ProductCatalogSteps(productCatalog);
        cartSteps = new CartSteps(cartPage);
        infoSteps = new CheckoutInformationSteps(infoPage);
        overviewSteps = new CheckoutOverviewSteps(overviewPage);
        completeSteps = new CheckoutCompleteSteps(completePage);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        log.info("=== START TEST TEARDOWN ===");

        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                log.error("Test FAILED: {}", result.getMethod().getMethodName());
                AllureAttachments.attachScreenshot();
                AllureAttachments.attachPageSource();
                AllureAttachments.attachCurrentUrl();
            }
        } finally {
            DriverManager.quitDriver();
            log.info("=== TEARDOWN COMPLETE ===");
        }
    }
}