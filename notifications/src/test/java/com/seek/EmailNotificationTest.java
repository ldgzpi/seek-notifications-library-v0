package com.seek;

import com.seek.model.EmailNotification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmailNotification")
class EmailNotificationTest {

    @Test
    @DisplayName("TEST #1: Debe construir EmailNotification con todos los campos")
    void shouldBuildEmailNotificationWithAllFields() {

        String expectedTo = "test@example.com";
        String expectedSubject = "Test Subject";
        String expectedContent = "Test Content";

        EmailNotification email = new EmailNotification.Builder()
                .to(expectedTo)
                .subject(expectedSubject)
                .content(expectedContent)
                .build();

        assertNotNull(email, "El email no debería ser null");
        assertEquals(expectedTo, email.getTo(), "El destinatario no coincide");
        assertEquals(expectedSubject, email.getSubject(), "El subject no coincide");
        assertEquals(expectedContent, email.getContent(), "El contenido no coincide");
        assertEquals("EMAIL", email.getType(), "El tipo debe ser EMAIL");
    }
}