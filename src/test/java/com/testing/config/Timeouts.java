package com.testing.config;

public final class Timeouts {
    
    private Timeouts() {
    }
    
    public static final int IMPLICIT_WAIT_SECONDS = 2;
    public static final int WEB_EXPLICIT_WAIT_SECONDS = 15;
    public static final int MOBILE_EXPLICIT_WAIT_SECONDS = 25;
    public static final int MOBILE_COMMAND_TIMEOUT_SECONDS = 120;
    public static final int SEARCH_RESULT_RETRY_ATTEMPTS = 6;
    public static final int SCROLL_ATTEMPTS = 12;
    public static final int UI_SCROLLABLE_MAX_SWIPES = 25;
}

