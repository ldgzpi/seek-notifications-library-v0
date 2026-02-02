package com.seek.provider;

import com.seek.core.NotificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FirebaseProvider implements PushProvider {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseProvider.class);

    private final String projectId;
    private final String name = "FirebaseFCM-Mock";

    public FirebaseProvider(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAccount() {
        return this.projectId;
    }

    @Override
    public void send(String deviceToken, String title, String body) {

        logger.info("[{}] Iniciando envío Push...", getName());

        try {

            if (deviceToken.equals("token-invalido")) {
                throw NotificationException.validationError("Simulación de error: Token inválido rechazado por FCM");
            }

            // Formato real aprox: projects/{project_id}/messages/{message_id}
            String fakeMessageId = String.format("projects/%s/messages/%s",
                    this.projectId,
                    UUID.randomUUID().toString()
            );

            logger.info("[{}] Notificación enviada correctamente. ID: {}", getName(), fakeMessageId);

        } catch (NotificationException e) {
            throw NotificationException.networkError(getName(), e);
        }
    }
}