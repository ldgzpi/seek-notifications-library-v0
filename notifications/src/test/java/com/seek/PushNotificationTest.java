package com.seek;

import com.seek.model.PushNotification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PushNotification")
class PushNotificationTest {

    @Test
    @DisplayName("Debe construir PushNotification con todos los campos")
    void shouldBuildPushNotificationWithAllFields() {
        String expectedToken = "fGhI1jKl2MnO3pQrS4tUvW5xYz6AbCdEfGhI7jKl8MnO9";
        String expectedTitle = "Nueva Oferta";
        String expectedBody = "50% de descuento en todos los productos";

        PushNotification push = new PushNotification.Builder()
                .token(expectedToken)
                .title(expectedTitle)
                .body(expectedBody)
                .build();

        assertNotNull(push, "El push no debería ser null");
        assertEquals(expectedToken, push.getDeviceToken(), "El device token no coincide");
        assertEquals(expectedTitle, push.getTitle(), "El título no coincide");
        assertEquals(expectedBody, push.getBody(), "El body no coincide");
        assertEquals("PUSH", push.getType(), "El tipo debe ser PUSH");
    }

    @Test
    @DisplayName("Debe usar los aliases del builder correctamente")
    void shouldUseBuilderAliasesCorrectly() {
        String deviceToken = "AbCdEfGhIjKlMnOpQrStUvWxYz123456";

        PushNotification push = new PushNotification.Builder()
                .token(deviceToken)        // Alias de to()
                .title("Recordatorio")
                .body("Tu cita es mañana")  // Alias de content()
                .build();

        assertEquals(deviceToken, push.getDeviceToken());
        assertEquals("Recordatorio", push.getTitle());
        assertEquals("Tu cita es mañana", push.getBody());

        // Verificamos que internamente usa to y content de BaseNotification
        assertEquals(deviceToken, push.getTo(), "token debería usar to internamente");
        assertEquals("Tu cita es mañana", push.getContent(), "body debería usar content internamente");

    }

    @Test
    @DisplayName("Debe permitir body null o vacío")
    void shouldAllowNullOrEmptyBody() {
        // Algunas push notifications solo tienen título, sin body
        PushNotification pushWithoutBody = new PushNotification.Builder()
                .token("AbCdEfGhIjKlMnOpQrStUvWxYz123456")
                .title("Nueva notificación")
                .build();

        assertNotNull(pushWithoutBody);
        assertEquals("Nueva notificación", pushWithoutBody.getTitle());
        assertNull(pushWithoutBody.getBody(), "Body puede ser null");
    }

    @Test
    @DisplayName("Debe manejar títulos con emojis")
    void shouldHandleEmojisInTitle() {
        String titleWithEmoji = "🎉 ¡Felicitaciones!";
        String bodyWithEmoji = "Has ganado un premio 🏆";

        PushNotification push = new PushNotification.Builder()
                .token("AbCdEfGhIjKlMnOpQrStUvWxYz123456")
                .title(titleWithEmoji)
                .body(bodyWithEmoji)
                .build();

        assertEquals(titleWithEmoji, push.getTitle());
        assertEquals(bodyWithEmoji, push.getBody());
        assertTrue(push.getTitle().contains("🎉"));
        assertTrue(push.getBody().contains("🏆"));
    }
}