package com.seek.provider;

public interface PushProvider extends Provider {
    void send(String deviceToken, String title, String body);
}