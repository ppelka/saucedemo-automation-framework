package com.ppelka.testbase;

import com.ppelka.core.ConfigReader;
import com.ppelka.core.DriverManager;
import com.ppelka.core.WebDriverFactory;
import com.ppelka.pageobjects.LoginPage;
import com.ppelka.steps.CartSteps;
import com.ppelka.steps.LoginSteps;
import com.ppelka.steps.ProductCatalogSteps;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected LoginPage loginPage;

    protected LoginSteps loginSteps;
    protected ProductCatalogSteps productSteps;
    protected CartSteps cartSteps;

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

        WebDriver driver = WebDriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        initPageObjects(driver);
        initSteps();

        String url = ConfigReader.get("base.url");
        log.info("Navigating to base URL: {}", url);
        driver.get(url);

        log.info("=== SETUP COMPLETE ===");
    }

    private void initPageObjects(WebDriver driver) {
        loginPage = new LoginPage(driver);
    }

    private void initSteps() {
        loginSteps = new LoginSteps(loginPage);
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
