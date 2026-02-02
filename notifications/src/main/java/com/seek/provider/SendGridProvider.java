package com.seek.provider;

import com.seek.core.NotificationException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SendGridProvider implements EmailProvider {

    private static final Logger logger = LoggerFactory.getLogger(SendGridProvider.class);

    private SendGrid sendGridClient;
    private String name = "SendGrid";
    private String account;
    private String defaultFrom;

    public SendGridProvider(String apikey, String accountIdentifier, String defaultFrom) {
        this.sendGridClient = new SendGrid(apikey);
        this.account = accountIdentifier;
        this.defaultFrom = defaultFrom;
    }

    public SendGridProvider(String apiKey) {
        this(apiKey, "Default-Account", "noreply@notifications.com");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAccount() {
        return this.account;
    }

    @Override
    public void send(String from, String to, String subject, String body) {

        Email fromEmail = new Email(from);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", body);
        String effectiveFrom = (from != null && !from.isBlank()) ? from : this.defaultFrom;

        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = new Response(); //Solo para simular una respuesta de la api

            if (response.getStatusCode() >= 400) {
                logger.error("[{}] Provider error: HTTP {} for recipient {}",
                        getName(), response.getStatusCode(), to);
                throw NotificationException.providerError(this.name, response.getStatusCode(), response.getBody());
            }

            logger.debug("[{}] Email sent successfully to: {}", getName(), to);

        } catch (NotificationException e) {
            logger.error("[{}] NotificationException while sending to {}: {}",
                    getName(), to, e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("[{}] Network error while sending to {}: {}",
                    getName(), to, e.getMessage(), e);
            throw NotificationException.networkError(this.name, e);
        }
    }
}