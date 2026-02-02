package com.seek.core;

import com.seek.channel.Channel;
import com.seek.dispatcher.EmailDispatcher;
import com.seek.dispatcher.PushDispatcher;
import com.seek.dispatcher.SmsDispatcher;
import com.seek.provider.EmailProvider;
import com.seek.provider.Provider;
import com.seek.provider.PushProvider;
import com.seek.provider.SmsProvider;

public class NotificationSender {

    public NotificationSender() {}

    public EmailDispatcher email(EmailProvider provider) {
        if (provider == null) {
            throw NotificationException.validationError("Debes especificar un EmailProvider.");
        }
        return new EmailDispatcher(this, provider);
    }

    public SmsDispatcher sms(SmsProvider provider) {
        if (provider == null) {
            throw NotificationException.validationError("Debes especificar un SmsProvider.");
        }
        return new SmsDispatcher(this, provider);
    }

    public PushDispatcher push(PushProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Debes especificar un PushProvider.");
        }
        return new PushDispatcher(this, provider);
    }

    public <T extends Provider> void send(Notification notification, Channel<T> channel, T provider) {
        try {
            if (notification == null) throw NotificationException.validationError("Notificación nula");

            channel.send(notification, provider);

        } catch (Exception e) {
            throw NotificationException.networkError(provider.toString(), e);
        }
    }
}