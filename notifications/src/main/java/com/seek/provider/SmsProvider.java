package com.seek.provider;

public interface SmsProvider extends Provider {
    void send(String phoneNumber, String content);
}