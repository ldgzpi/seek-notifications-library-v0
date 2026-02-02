package com.seek.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioProvider implements SmsProvider {

    private final String accountSid;
    private static final Logger logger = LoggerFactory.getLogger(TwilioProvider.class);

    public TwilioProvider(String accountSid, String authToken) {
        this.accountSid = accountSid;
    }

    @Override
    public String getName() { return "Twilio"; }

    @Override
    public String getAccount() { return accountSid; }

    @Override
    public void send(String phoneNumber, String message) {
        logger.info("[{}] Connecting to Twilio API...", getName());
        logger.info("[{}] Sending SMS to: {}", getName(), phoneNumber);
        logger.debug("[{}] Body: {}", getName(), message);
        logger.info("[{}] Status: 200 OK", getName());
    }
}