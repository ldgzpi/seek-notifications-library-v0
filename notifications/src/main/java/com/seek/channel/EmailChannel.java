package com.seek.channel;

import com.seek.core.Notification;
import com.seek.core.NotificationValidator;
import com.seek.model.EmailNotification;
import com.seek.provider.EmailProvider;

public class EmailChannel implements Channel<EmailProvider> {

    @Override
    public void send(Notification notification, EmailProvider provider) {
        if (!(notification instanceof EmailNotification)) {
            throw new IllegalArgumentException("EmailChannel espera una EmailNotification, pero recibió: " + notification.getClass().getSimpleName());
        }

        EmailNotification emailSpec = (EmailNotification) notification;

        NotificationValidator.validateEmail(emailSpec);

        provider.send(
            null, // Reemplazado por defaultfrom
            emailSpec.getTo(),
            emailSpec.getSubject(),
            emailSpec.getContent()
        );

    }
}