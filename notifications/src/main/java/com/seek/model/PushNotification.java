package com.seek.model;

import com.seek.core.BaseNotification;
import com.seek.core.Notification;

public class PushNotification extends BaseNotification {

    private final String title;

    private PushNotification(Builder builder) {
        super(builder);
        this.title = builder.title;
    }

    public String getTitle() {
        return title;
    }

    public String getDeviceToken() {
        return this.to;
    }

    public String getBody() {
        return this.content;
    }

    @Override
    public String getType() {
        return "PUSH";
    }

    public static class Builder extends BaseNotification.Builder<Builder> {

        private String title;

        @Override
        protected Builder self() { return this; }

        public Builder token(String deviceToken) {
            return this.to(deviceToken);
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder body(String body) {
            return this.content(body);
        }

        @Override
        public PushNotification build() {
            return new PushNotification(this);
        }
    }
}