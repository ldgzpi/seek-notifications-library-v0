package com.seek;

import com.seek.model.SmsNotification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SmsNotification")
class SmsNotificationTest {

    @Test
    @DisplayName("Debe construir SmsNotification con todos los campos")
    void shouldBuildSmsNotificationWithAllFields() {
        String expectedPhoneNumber = "+541112345678";
        String expectedMessage = "Tu código de verificación es: 123456";

        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber(expectedPhoneNumber)
                .message(expectedMessage)
                .build();

        assertNotNull(sms, "El SMS no debería ser null");
        assertEquals(expectedPhoneNumber, sms.getPhoneNumber(), "El número de teléfono no coincide");
        assertEquals(expectedMessage, sms.getMessage(), "El mensaje no coincide");
        assertEquals("SMS", sms.getType(), "El tipo debe ser SMS");
    }

    @Test
    @DisplayName("Debe usar los aliases del builder correctamente")
    void shouldUseBuilderAliasesCorrectly() {
        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber("+5491187654321")
                .message("Hola desde SMS")
                .build();

        assertEquals("+5491187654321", sms.getPhoneNumber());
        assertEquals("Hola desde SMS", sms.getMessage());

        // Verificamos que internamente usa to y content de BaseNotification
        assertEquals("+5491187654321", sms.getTo(), "phoneNumber debería usar to internamente");
        assertEquals("Hola desde SMS", sms.getContent(), "message debería usar content internamente");
    }

    @Test
    @DisplayName("Debe permitir mensajes con caracteres especiales")
    void shouldAllowSpecialCharactersInMessage() {
        String messageWithSpecialChars = "¡Hola! Tu código es: #123 ñáéíóú €";

        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber("+5491123456789")
                .message(messageWithSpecialChars)
                .build();

        assertEquals(messageWithSpecialChars, sms.getMessage());
        assertTrue(sms.getMessage().contains("ñáéíóú"));
        assertTrue(sms.getMessage().contains("€"));
    }
}