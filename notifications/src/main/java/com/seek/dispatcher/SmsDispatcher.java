package com.seek.dispatcher;

import com.seek.channel.SmsChannel;
import com.seek.core.Notification;
import com.seek.core.NotificationValidator;
import com.seek.core.NotificationSender;
import com.seek.model.SmsNotification;
import com.seek.provider.SmsProvider;

public class SmsDispatcher {

    private final NotificationSender sender;
    private final SmsProvider provider;

    private final SmsChannel channel = new SmsChannel();

    public SmsDispatcher(NotificationSender sender, SmsProvider provider) {
        this.sender = sender;
        this.provider = provider;
    }

    public void send(String phoneNumber, String message) {

        Notification notification = new SmsNotification.Builder()
                .phoneNumber(phoneNumber)
                .message(message)
                .build();

        sender.send(notification, this.channel, this.provider);
    }
}