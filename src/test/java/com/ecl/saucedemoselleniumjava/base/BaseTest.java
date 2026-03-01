package com.ecl.saucedemoselleniumjava.base;

import com.ecl.saucedemoselleniumjava.config.TestConfig;
import io.qameta.allure.Attachment;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Project: sauceDemoSelleniumJava File: BaseTest
 * Description: Base test configuration to initialize and close the driver per test.
 *
 * @author Elmer Coronel (Elmer)
 * @version 1.0
 * @since 28/02/2026
 */
@Listeners({AllureTestNg.class})
public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver(TestConfig.browser());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot();
        }
        DriverManager.quitDriver();
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] attachScreenshot() {
        if (DriverManager.getDriver() instanceof TakesScreenshot takesScreenshot) {
            return takesScreenshot.getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }
}
