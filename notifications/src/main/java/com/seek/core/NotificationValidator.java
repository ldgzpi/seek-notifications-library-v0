package com.seek.core;

import com.seek.model.EmailNotification;
import com.seek.model.PushNotification;
import com.seek.model.SmsNotification;

public class NotificationValidator {

    public static void validateEmail(EmailNotification notification) {
        if (notification == null) {
            throw NotificationException.validationError("Email notification cannot be null");
        }
        if (notification.getTo() == null || notification.getTo().isBlank()) {
            throw NotificationException.validationError("Recipient email is required");
        }
        if (!isValidEmail(notification.getTo())) {
            throw NotificationException.validationError("Invalid email format: " + notification.getTo());
        }
        if (notification.getSubject() == null || notification.getSubject().isBlank()) {
            throw NotificationException.validationError("Email subject is required");
        }
        if (notification.getContent() == null || notification.getContent().isBlank()) {
            throw NotificationException.validationError("Email content is required");
        }
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static void validateSms(SmsNotification notification) {
        if (notification == null) {
            throw NotificationException.validationError("SMS notification cannot be null");
        }

        if (notification.getPhoneNumber() == null || notification.getPhoneNumber().isBlank()) {
            throw NotificationException.validationError("Phone number is required");
        }

        if (!isValidPhoneNumber(notification.getPhoneNumber())) {
            throw NotificationException.validationError("Invalid phone number format: " + notification.getPhoneNumber());
        }

        if (notification.getMessage() == null || notification.getMessage().isBlank()) {
            throw NotificationException.validationError("SMS message is required");
        }

        // Los SMS tienen límite de caracteres (160 para GSM, 70 para Unicode)
        if (notification.getMessage().length() > 160) {
            throw NotificationException.validationError("SMS message exceeds 160 character limit");
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Acepta formatos internacionales como +1234567890, +54 11 1234-5678, etc.
        // Debe empezar con + y tener al menos 8 dígitos
        String cleanNumber = phoneNumber.replaceAll("[\\s\\-()]", "");
        return cleanNumber.matches("^\\+\\d{8,15}$");
    }

    public static void validatePush(PushNotification notification) {
        if (notification == null) {
            throw NotificationException.validationError("Push notification cannot be null");
        }

        if (notification.getDeviceToken() == null || notification.getDeviceToken().isBlank()) {
            throw NotificationException.validationError("Device token is required");
        }

        // Los device tokens suelen tener un formato específico (hexadecimal, longitud mínima)
        if (notification.getDeviceToken().length() < 32) {
            throw NotificationException.validationError("Device token is too short (minimum 32 characters)");
        }

        if (notification.getTitle() == null || notification.getTitle().isBlank()) {
            throw NotificationException.validationError("Push notification title is required");
        }

        // Límites recomendados para Push Notifications
        if (notification.getTitle().length() > 50) {
            throw NotificationException.validationError("Push title exceeds 50 character limit");
        }

        if (notification.getBody() != null && notification.getBody().length() > 200) {
            throw NotificationException.validationError("Push body exceeds 200 character limit");
        }
    }
}
