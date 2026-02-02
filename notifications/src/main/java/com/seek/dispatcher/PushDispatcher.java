package com.seek.dispatcher;

import com.seek.channel.PushChannel;
import com.seek.core.Notification;
import com.seek.core.NotificationSender;
import com.seek.model.PushNotification;
import com.seek.provider.PushProvider;

public class PushDispatcher {

    private final NotificationSender sender;
    private final PushProvider provider;
    private final PushChannel channel = new PushChannel();

    public PushDispatcher(NotificationSender sender, PushProvider provider) {
        this.sender = sender;
        this.provider = provider;
    }

    public void send(String token, String title, String body) {

        Notification notification = new PushNotification.Builder()
                .token(token)
                .title(title)
                .body(body)
                .build();

        sender.send(notification, this.channel, this.provider);
    }
}