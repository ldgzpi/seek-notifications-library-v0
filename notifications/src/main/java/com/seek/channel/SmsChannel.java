package com.seek.channel;

import com.seek.core.Notification;
import com.seek.core.NotificationValidator;
import com.seek.model.SmsNotification;
import com.seek.provider.SmsProvider;

public class SmsChannel implements Channel<SmsProvider> {

    @Override
    public void send(Notification notification, SmsProvider provider) {

        SmsNotification sms = (SmsNotification) notification;

        NotificationValidator.validateSms(sms);

        provider.send(sms.getPhoneNumber(), sms.getMessage());
    }
}