package com.testing.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnboardingPage extends BaseMobilePage {

    private static final Logger logger = LoggerFactory.getLogger(OnboardingPage.class);
    private static final By SKIP_BUTTON = AppiumBy.id("org.wikipedia:id/fragment_onboarding_skip_button");

    public OnboardingPage(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void skipIfPresent() {
        if (driver == null || wait == null) return;
        try {
            if (!driver.findElements(SKIP_BUTTON).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(SKIP_BUTTON)).click();
            }
        } catch (TimeoutException e) {
            logger.debug("Skip button not found or not clickable, onboarding may have been completed: {}", e.getMessage());
        }
    }
}
