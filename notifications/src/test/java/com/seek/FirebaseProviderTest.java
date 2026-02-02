package com.seek;

import com.seek.core.NotificationException;
import com.seek.provider.FirebaseProvider;
import com.seek.provider.Provider;
import com.seek.provider.PushProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FirebaseProvider")
class FirebaseProviderTest {

    @Test
    @DisplayName("Debe crear provider con projectId")
    void shouldCreateProviderWithProjectId() {
        FirebaseProvider provider = new FirebaseProvider("my-firebase-project");

        assertNotNull(provider);
        assertEquals("FirebaseFCM-Mock", provider.getName());
        assertEquals("my-firebase-project", provider.getAccount());
    }

    @Test
    @DisplayName("Debe implementar las interfaces correctas")
    void shouldImplementCorrectInterfaces() {
        FirebaseProvider provider = new FirebaseProvider("my-firebase-project");

        assertInstanceOf(PushProvider.class, provider);
        assertInstanceOf(Provider.class, provider);
    }

    @Test
    @DisplayName("Debe permitir envío simulado sin errores")
    void shouldAllowSimulatedSendWithoutErrors() {
        FirebaseProvider provider = new FirebaseProvider("my-firebase-project");

        assertDoesNotThrow(() ->
                provider.send("valid-token-123", "Test Title", "Test Body")
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con token inválido simulado")
    void shouldThrowExceptionWithInvalidToken() {
        FirebaseProvider provider = new FirebaseProvider("my-firebase-project");

        assertThrows(NotificationException.class, () ->
                provider.send("token-invalido", "Test", "Body")
        );
    }
}