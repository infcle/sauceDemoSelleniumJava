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
|   +-- BasePage.java
|   +-- BaseTest.java
|   +-- DriverManager.java
+-- config
|   +-- TestConfig.java
+-- model
|   +-- ProductData.java
+-- pages
|   +-- LoginPage.java
|   +-- InventoryPage.java
|   +-- ProductPage.java
|   +-- CartPage.java
|   +-- CheckoutPage.java
|   +-- CheckoutOverviewPage.java
|   +-- CheckoutCompletePage.java
+-- tests
    +-- LoginTest.java
    +-- PurchaseFlowTest.java
```

## Features
- Page Object Model (POM) architecture
- Multi-browser execution (`chrome`, `firefox`, `edge`, `safari`, `chromium`)
- Driver lifecycle management with `ThreadLocal<WebDriver>`
- Browser popup mitigation for password manager interruptions (Chrome/Edge)
- Centralized test config through YAML + JVM properties (`TestConfig`)
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
Use Maven:

```bash
# Windows
mvn test

# macOS/Linux
mvn test
```

Run with a specific browser:

```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
mvn test -Dbrowser=safari
mvn test -Dbrowser=chromium
```

Default test data/configuration lives in:

```text
src/test/resources/test-config.yml
```

You can use placeholder syntax in YAML values:
`$\{ENV_VAR:default_value}`

Priority used by the framework:
- Environment variable
- JVM property (`-D...`)
- `test-config.yml`

Environment variable names:
- `BROWSER`
- `BASE_URL`
- `USER_STANDARD`
- `USER_PASSWORD`
- `CHECKOUT_FIRST_NAME`
- `CHECKOUT_LAST_NAME`
- `CHECKOUT_ZIP_CODE`

You can still override any value with JVM properties:

```bash
mvn test ^
  -Dbase.url=https://www.saucedemo.com ^
  -Duser.standard=standard_user ^
  -Duser.password=secret_sauce ^
  -Dcheckout.firstName=Elmer ^
  -Dcheckout.lastName=Coronel ^
  -Dcheckout.zipCode=00000
```

## Generate Allure Report
```bash
mvn clean test
allure generate target/allure-results --clean -o target/allure-report
allure open target/allure-report

# or run report in a temporary local server
allure serve target/allure-results
```

Allure results are generated in:
- `target/allure-results`

## Main Test Scenarios
- `LoginTest.loginWithValidUser`
  - Validates successful login and products page visibility.

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
