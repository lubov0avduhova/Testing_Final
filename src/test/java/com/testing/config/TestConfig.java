package com.testing.config;

public final class TestConfig {

    private TestConfig() {
    }

    public static String webBaseUrl() {
        return System.getProperty("webBaseUrl", "https://en.wikipedia.org");
    }

    public static String appiumServerUrl() {
        return System.getProperty("appiumServerUrl", "http://127.0.0.1:4723");
    }

    public static String deviceName() {
        return System.getProperty("deviceName", "Android Emulator");
    }

    public static String platformVersion() {
        return System.getProperty("platformVersion", "");
    }

    public static String browser() {
        return System.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
