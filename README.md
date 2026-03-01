# SauceDemo Selenium Java Framework

UI automation framework for [SauceDemo](https://www.saucedemo.com/) using Java, TestNG, Selenium WebDriver, and Allure Reports.

## Tech Stack
- Java 17
- Maven
- Selenium WebDriver 4
- TestNG
- Allure TestNG
- WebDriverManager

## Project Structure
```text
src/test/java/com/ecl/saucedemoselleniumjava
+-- base
¦   +-- BasePage.java
¦   +-- BaseTest.java
¦   +-- DriverManager.java
+-- model
¦   +-- ProductData.java
+-- pages
¦   +-- LoginPage.java
¦   +-- InventoryPage.java
¦   +-- ProductPage.java
¦   +-- CartPage.java
¦   +-- CheckoutPage.java
¦   +-- CheckoutOverviewPage.java
¦   +-- CheckoutCompletePage.java
+-- tests
    +-- LoginTest.java
    +-- PurchaseFlowTest.java
```

## Features
- Page Object Model (POM) architecture
- Multi-browser execution (`chrome`, `firefox`, `edge`, `safari`, `chromium`)
- Driver lifecycle management with `ThreadLocal<WebDriver>`
- Browser popup mitigation for password manager interruptions (Chrome/Edge)
- Allure integration with:
  - test metadata (`@Epic`, `@Feature`, `@Story`, `@Severity`, `@Description`)
  - step-level traceability (`@Step`)
  - screenshot attachment on failure
- End-to-end purchase flow validation:
  - cart item data and quantity
  - checkout step-one form validation
  - checkout step-two item validation
  - totals consistency (`item total + tax = total`)
  - checkout completion message validation

## Prerequisites
- JDK 17 installed
- Maven installed (or use Maven Wrapper included in this repo)

## Run Tests
Use Maven Wrapper (recommended):

```bash
# Windows
mvnw.cmd test

# macOS/Linux
./mvnw test
```

Run with a specific browser:

```bash
mvnw.cmd test -Dbrowser=chrome
mvnw.cmd test -Dbrowser=firefox
mvnw.cmd test -Dbrowser=edge
mvnw.cmd test -Dbrowser=safari
mvnw.cmd test -Dbrowser=chromium
```

## Generate Allure Report
```bash
mvnw.cmd clean test
mvnw.cmd allure:report
mvnw.cmd allure:serve
```

Allure results are generated in:
- `target/allure-results`

## Main Test Scenarios
- `LoginTest.loginWithValidUser`
  - Validates successful login and inventory page visibility.

- `PurchaseFlowTest.e2ePurchaseFlow`
  - Login
  - Add product to cart
  - Validate cart data
  - Checkout step one validation
  - Checkout overview validation (item + totals)
  - Finish order and validate completion message

## Notes
- This project is test-focused (`src/test/java`).
- Browser drivers are handled automatically through WebDriverManager.
- If you run tests in parallel later, the current `ThreadLocal` driver design already supports safe isolation.
