🧪 SauceDemo Automation Framework

UI test automation framework for SauceDemo built with Selenium WebDriver, TestNG, Allure, and the Page Object Model.Designed for clean architecture, rich reporting, and CI/CD integration.

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

src/
├── main/
│   └── java/com.ppelka/
│       ├── abstractcomponents/
│       ├── core/
│       └── pageobjects/
├── test/
│   ├── java/com.ppelka/
│   │   ├── steps/
│   │   ├── testbase/
│   │   └── tests/
│   └── resources/
│       ├── config.properties
│       ├── allure.properties
│       └── testng.xml

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

Custom RetryAnalyzer retries failed tests once

Integrated with TestNG listeners

All retries visible in Allure report

⚙️ Configuration

All configuration is stored in:

src/test/resources/config.properties

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

[ ] Add GitHub Actions workflow

[ ] Add badges

[ ] Add architecture diagram

📜 License

This project is licensed under the MIT License.