package com.testing.tests.mobile;

import com.testing.config.Timeouts;
import com.testing.driver.MobileDriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public abstract class BaseMobileTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseMobileTest.class);
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUpDriver() {
        try {
            logger.info("Setting up AndroidDriver");
            driver = MobileDriverFactory.createAndroidDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(Timeouts.MOBILE_EXPLICIT_WAIT_SECONDS));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Timeouts.IMPLICIT_WAIT_SECONDS));
            logger.info("AndroidDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize AndroidDriver", e);
            driver = null;
            wait = null;
            throw new SkipException("Appium session not started: " + e.getMessage(), e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            takeScreenshot(result.getMethod().getMethodName());
        }
        if (driver != null) {
            logger.info("Closing AndroidDriver");
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        try {
            File screenshot = driver.getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + testName + "_" + System.currentTimeMillis() + ".png");
            destination.getParentFile().mkdirs();
            FileHandler.copy(screenshot, destination);
            logger.error("Screenshot saved: {}", destination.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
        }
    }

    protected boolean isElementPresent(List<WebElement> elements) {
        return elements != null && !elements.isEmpty();
    }
}
