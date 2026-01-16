🧪 SauceDemo Automation Framework

UI test automation framework for SauceDemo built with Selenium WebDriver, TestNG, Allure, and the Page Object Model.Designed for clean architecture, rich reporting, and CI/CD integration.

![Build](https://github.com/ppelka/saucedemo-automation-framework/actions/workflows/maven-tests.yml/badge.svg)

🚀 Technologies Used

Java 17

Selenium WebDriver

TestNG

Allure Reporting

Maven

GitHub Actions (CI/CD)

Page Object Model (POM)

Retry Analyzer + Listener

Custom Config Reader

🧱 Project Structure

- src/
    - main/
        - java/com.ppelka/
            - abstractcomponents/
            - core/
            - pageobjects/
    - test/
        - java/com.ppelka/
            - steps/
            - testbase/
            - tests/
        - resources/
            - config.properties
            - allure.properties
            - testng.xml


🧪 How to Run Tests

1. Install dependencies

mvn clean install

2. Run tests

mvn test

3. Generate Allure Report

allure serve target/allure-results

📊 Allure Reporting

Each test step is annotated with @Step

Automatic screenshot capture on failure

Attachments for logs and diagnostics

Results stored in target/allure-results

🔁 Retry Logic

A custom RetryAnalyzer retries failed tests once

Retry is integrated with TestNG listeners so Allure records every attempt

Allure displays which run passed or failed and includes diagnostics for each attempt

Intentional failing test for demonstration

One test is intentionally configured to fail to demonstrate retry behavior and Allure diagnostics:

Class AddProductToCartAndRemoveItTest

Method addAndRemoveProductTest

The final assertion is commented out intentionally:

    // Intentionally commented out to demonstrate retry and Allure failure reporting 

    // .verifyCartIsEmpty();

This is deliberate to show:

retry in action

how Allure captures screenshots, logs, and stacktraces for failed attempts

how Maven reports final build status when retries ultimately fail

A short note about interpretation

Seeing a failed test with retry is expected in this demo setup and is used to showcase diagnostics and robustness of reporting.

⚙️ Configuration

All configuration is stored in: src/test/resources/config.properties

Loaded via ConfigReader class.

🧪 Sample Test Scenarios

✔ Valid login

❌ Invalid login

🛒 Add product to cart

🧹 Remove product from cart

🔍 Verify product catalog

🔄 Multi-step product flow (coming soon)

📦 CI/CD Integration (coming soon)

Planned GitHub Actions workflow:

Build project

Run tests

Generate Allure results

Upload report as artifact

Add build & test badges to README

💡 Why This Project Matters

This framework demonstrates:

Clean automation architecture

Real-world testing patterns

Reporting and diagnostics

CI/CD readiness

Portfolio-quality engineering

📌 Future Improvements

[ ] Add more test scenarios

[ ] Add Docker Selenium Grid

[ ] Add environment switching

[ ] Add badges

[ ] Add architecture diagram

📜 License

This project is licensed under the MIT License.
