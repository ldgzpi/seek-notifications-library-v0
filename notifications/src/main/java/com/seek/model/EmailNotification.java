package com.seek.model;

import com.seek.core.BaseNotification;
import com.seek.core.Notification;

public class EmailNotification extends BaseNotification {
    
    private final String subject;

    private EmailNotification(Builder builder) {
        super(builder);
        this.subject = builder.subject;
    }

    public String getSubject() { return subject; }

    @Override
    public String getType() { return "EMAIL"; }

    public static class Builder extends BaseNotification.Builder<Builder> {
        
        private String subject;

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public EmailNotification build() {
            return new EmailNotification(this);
        }
    }
}