package com.ecl.saucedemoselleniumjava.base;

import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Map;

/**
 * Project: sauceDemoSelleniumJava File: DriverManager
 * Description: Centralized WebDriver lifecycle and browser selection management.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void initDriver(String browser) {
        if (getDriver() != null) {
            return;
        }

        String browserName = normalizeBrowser(browser);
        WebDriver driver = createDriver(browserName);
        driver.manage().window().maximize();

        DRIVER.set(driver);
        WebDriverRunner.setWebDriver(driver);
    }

    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static String normalizeBrowser(String browser) {
        if (browser == null || browser.isBlank()) {
            return "chrome";
        }
        return browser.toLowerCase().trim();
    }

    private static WebDriver createDriver(String browser) {
        return switch (browser) {
            case "chrome", "chromium" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(chromeOptions());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(edgeOptions());
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            case "safari" -> new SafariDriver();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Use: chrome, edge, firefox, safari, chromium"
            );
        };
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-notifications",
                "--disable-features=PasswordLeakDetection,PasswordCheck",
                "--disable-save-password-bubble"
        );
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));
        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(
                "--disable-notifications",
                "--disable-features=PasswordLeakDetection,PasswordCheck",
                "--disable-save-password-bubble"
        );
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));
        return options;
    }
}
