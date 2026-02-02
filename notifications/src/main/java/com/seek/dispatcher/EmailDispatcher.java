package com.seek.dispatcher;

import com.seek.core.NotificationException;
import com.seek.model.EmailNotification;
import com.seek.provider.EmailProvider;
import com.seek.core.NotificationSender;
import com.seek.channel.EmailChannel;

public class EmailDispatcher {

    private final NotificationSender sender;
    private final EmailProvider provider;

    private final EmailChannel channel = new EmailChannel();

    public EmailDispatcher(NotificationSender sender, EmailProvider provider) {
        this.sender = sender;
        this.provider = provider;
    }

    public void send(String to, String subject, String content) {
            EmailNotification notification = new EmailNotification.Builder()
                    .to(to)
                    .subject(subject)
                    .content(content)
                    .build();

            sender.send(notification, this.channel, this.provider);
    }
}