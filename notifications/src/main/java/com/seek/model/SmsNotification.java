package com.seek.model;

import com.seek.core.BaseNotification;
import com.seek.core.Notification;

public class SmsNotification extends BaseNotification {

    private SmsNotification(Builder builder) {
        super(builder);
    }

    public String getPhoneNumber() {
        return this.to;
    }

    public String getMessage() {
        return this.content;
    }
    @Override
    public String getType() { return "SMS"; }

    public static class Builder extends BaseNotification.Builder<Builder> {

        public Builder phoneNumber(String phoneNumber) {
            return this.to(phoneNumber);
        }

        public Builder message(String message) {
            return this.content(message);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SmsNotification build() {
            return new SmsNotification(this);
        }
    }
}