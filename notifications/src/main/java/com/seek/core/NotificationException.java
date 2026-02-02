package com.seek.core;

public class NotificationException extends RuntimeException {
    private final String errorCode;
    private final String provider;

    public NotificationException(String errorCode, String message, String provider, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.provider = provider;
    }

    public static NotificationException networkError(String provider, Throwable cause) {
        return new NotificationException("NET-004", "Network error", provider, cause);
    }

    public static NotificationException validationError(String message) {
        return new NotificationException("VAL-002", message, null, null);
    }

    public static NotificationException providerError(String provider, int statusCode, String body) {
        return new NotificationException("PRV-" + statusCode,
                "Provider error: " + body, provider, null);
    }
}
