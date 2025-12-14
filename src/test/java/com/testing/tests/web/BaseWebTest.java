package com.testing.tests.web;

import com.testing.config.TestConfig;
import com.testing.config.Timeouts;
import com.testing.driver.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public abstract class BaseWebTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseWebTest.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Setting up WebDriver for browser: {}", TestConfig.browser());
        driver = WebDriverFactory.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Timeouts.WEB_EXPLICIT_WAIT_SECONDS));
        driver.get(TestConfig.webBaseUrl());
        logger.info("Navigated to: {}", TestConfig.webBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            takeScreenshot(result.getMethod().getMethodName());
        }
        if (driver != null) {
            logger.info("Closing WebDriver");
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("screenshots/" + testName + "_" + System.currentTimeMillis() + ".png");
            destination.getParentFile().mkdirs();
            FileHandler.copy(screenshot, destination);
            logger.error("Screenshot saved: {}", destination.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
        }
    }
}
