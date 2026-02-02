package com.seek.provider;

public interface EmailProvider extends Provider {
    void send(String from, String to, String subject, String body);
}