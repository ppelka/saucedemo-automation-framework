# 🧱 Architecture Overview

This document provides a deeper, technical explanation of the architecture behind the SauceDemo Automation Framework.  
It complements the high‑level README by describing the internal structure, design decisions, and engineering principles that make the framework scalable, maintainable, and production‑ready.

---

## 🏗 Layered Architecture

The framework follows a clean, modular, multi‑layer architecture:
```
Tests → Steps → Page Objects → AbstractComponent → Core Utilities → WebDriver
```

Each layer has a single responsibility and communicates only with the layer directly below it.

### **1. Test Layer**
- Contains only scenario flow.
- No Page Object usage.
- Uses fluent Steps API.
- Categorized using TestNG groups (`smoke`, `regression`).
- Fully annotated with Allure metadata (Epic, Feature, Story, Severity).

### **2. Steps Layer (Business Logic)**
- Represents user actions, not UI interactions.
- All methods annotated with `@Step` for Allure reporting.
- Provides fluent, readable test flow.
- Delegates UI operations to Page Objects.

### **3. Page Objects**
- Encapsulate locators and UI interactions.
- Contain no business logic.
- Delegate shared behavior to `AbstractComponent`.

### **4. AbstractComponent**
- Provides stable UI interaction helpers:
    - explicit waits
    - safe click
    - safe typing
    - JS fallback
    - element lookup
- Ensures consistent behavior across all Page Objects.

### **5. Core Utilities**
- `DriverManager` — ThreadLocal WebDriver lifecycle.
- `WebDriverFactory` — browser configuration.
- `ConfigReader` — layered configuration (system properties → config file → defaults).

### **6. WebDriver**
- Selenium WebDriver executes browser actions.
- Managed centrally to ensure stability and isolation.

---

## 🔧 Driver Lifecycle

The driver lifecycle is fully centralized in `BaseTest` and `DriverManager`.

### **DriverManager**
- Uses `ThreadLocal<WebDriver>` for thread safety.
- Ensures isolation for parallel execution.

### **BaseTest**
- Initializes:
    - WebDriver
    - Page Objects
    - Steps
- Handles teardown and cleanup.
- Ensures consistent environment for each test.

---

## 🧬 Allure Integration

The framework uses **AspectJ weaving** to automatically intercept all `@Step` methods.

### What this enables:
- Step‑level reporting without manual logging.
- Automatic screenshots on failure.
- Retry history grouped per test.
- Behaviors view structured by:
    - Epic
    - Feature
    - Story
    - Severity

### Allure artifacts:
- Stored in `target/allure-results`
- Uploaded in CI per JDK version (matrix build)

---

## 🔁 Retry Logic

A custom `RetryAnalyzer`:
- re‑runs failed tests once,
- integrates with TestNG listeners,
- produces full retry history in Allure.

This improves stability and helps diagnose flaky behavior.

---

## 🧪 Test Strategy

### **Smoke tests**
- Fast, critical-path scenarios.
- Validate core functionality (login, checkout).

### **Regression tests**
- Broader coverage.
- Include negative and edge cases.

### **Data-driven tests**
- Implemented via TestNG `@DataProvider`.
- Example: login verification for multiple user types.

---

## 🚀 CI/CD Architecture

The GitHub Actions pipeline:
- runs on every push/PR,
- executes tests on **JDK 17 and 21** (matrix),
- caches Maven dependencies,
- uploads Allure results per JDK version.

This ensures:
- forward compatibility,
- reproducible builds,
- fast feedback loops.

---

## 📦 Project Structure
```
- src/
    - main/
        - java/com.ppelka/
            - abstractcomponents/
            - core/
            - pageobjects/
    - test/
        - java/com.ppelka/
            - data/
            - listeners/
            - steps/
            - testbase/
            - tests/
            - utils/
        - resources/
            - config.properties
            - allure.properties
```
---

## 🧠 Design Principles

The framework is built on the following engineering principles:

- **Single Responsibility Principle**  
  Each layer has one clear purpose.

- **Separation of Concerns**  
  Tests do not know about UI structure; Page Objects do not know about business logic.

- **Defensive Programming**  
  Explicit waits, safe interactions, fallback mechanisms.

- **Extensibility**  
  Easy to add new Steps, Pages, or utilities.

- **Observability**  
  Rich Allure reporting with full diagnostics.

- **CI-first mindset**  
  Pipeline is stable, fast, and compatible with multiple JDK versions.

---

## 📌 Future Architectural Enhancements

- Parallel execution (methods/tests)
- Selenium Grid / Docker support
- API testing module in the same repo
- Environment profiles (local/qa/stage)
- Page Object composition for reusable UI fragments

---

If you want to extend or modify the architecture, this document should serve as the foundation for future decisions.
