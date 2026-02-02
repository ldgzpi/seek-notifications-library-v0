package com.seek.channel;

import com.seek.core.Notification;
import com.seek.core.NotificationValidator;
import com.seek.model.PushNotification;
import com.seek.provider.PushProvider;

public class PushChannel implements Channel<PushProvider> {

    @Override
    public void send(Notification notification, PushProvider provider) {
        if (!(notification instanceof PushNotification)) {
            throw new IllegalArgumentException("PushChannel espera PushNotification.");
        }

        PushNotification push = (PushNotification) notification;

        NotificationValidator.validatePush(push);

        provider.send(push.getDeviceToken(), push.getTitle(), push.getBody());
    }
}