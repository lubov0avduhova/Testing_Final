package com.testing.driver;

import com.testing.config.TestConfig;
import com.testing.config.Timeouts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public final class MobileDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(MobileDriverFactory.class);

    private MobileDriverFactory() {
    }

    public static AndroidDriver createAndroidDriver() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(TestConfig.deviceName());
        options.setUdid(TestConfig.deviceName());
        if (!TestConfig.platformVersion().isEmpty()) {
            options.setPlatformVersion(TestConfig.platformVersion());
        }
        options.setAppPackage("org.wikipedia");
        options.setAppActivity("org.wikipedia.main.MainActivity");
        options.setNoReset(true);
        options.setNewCommandTimeout(Duration.ofSeconds(Timeouts.MOBILE_COMMAND_TIMEOUT_SECONDS));
        options.setAutoGrantPermissions(true);
        options.setCapability("forceAppLaunch", true);

        String baseUrl = TestConfig.appiumServerUrl();
        logger.info("Connecting to Appium server at: {}", baseUrl);
        try {
            return new AndroidDriver(new URL(baseUrl), options);
        } catch (WebDriverException first) {
            // Appium 2/3 по умолчанию слушает без /wd/hub; пробуем альтернативный путь.
            logger.warn("Failed to connect with URL: {}, trying alternative path", baseUrl);
            String fallbackUrl = toggleHubPath(baseUrl);
            if (fallbackUrl.equals(baseUrl)) {
                throw first;
            }
            try {
                logger.info("Trying alternative URL: {}", fallbackUrl);
                return new AndroidDriver(new URL(fallbackUrl), options);
            } catch (MalformedURLException e) {
                logger.debug("Fallback URL is malformed: {}", fallbackUrl, e);
                throw first;
            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL: " + baseUrl, e);
        }
    }

    private static String toggleHubPath(String url) {
        if (url.endsWith("/wd/hub")) {
            return url.replace("/wd/hub", "");
        }
        if (url.endsWith("/")) {
            return url + "wd/hub";
        }
        return url + "/wd/hub";
    }
}
