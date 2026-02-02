package com.seek;

import com.seek.channel.PushChannel;
import com.seek.core.Notification;
import com.seek.core.NotificationException;
import com.seek.model.PushNotification;
import com.seek.provider.PushProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PushChannel")
class PushChannelTest {

    @Mock
    private PushProvider providerMock;

    @Mock
    private Notification genericNotificationMock;

    private PushChannel channel;

    @BeforeEach
    void setUp() {
        channel = new PushChannel();
    }

    @Test
    @DisplayName("Debe enviar PushNotification correctamente")
    void shouldSendPushNotification() {
        PushNotification push = new PushNotification.Builder()
                .token("fGhI1jKl2MnO3pQrS4tUvW5xYz6AbCdEfGhI7jKl8MnO9")
                .title("Nueva Oferta")
                .body("50% de descuento")
                .build();

        channel.send(push, providerMock);

        verify(providerMock, times(1)).send(
                "fGhI1jKl2MnO3pQrS4tUvW5xYz6AbCdEfGhI7jKl8MnO9",
                "Nueva Oferta",
                "50% de descuento"
        );
    }

    @Test
    @DisplayName("Debe pasar los parámetros correctos al provider")
    void shouldPassCorrectParametersToProvider() {
        PushNotification push = new PushNotification.Builder()
                .token("AbCdEfGhIjKlMnOpQrStUvWxYz123456")
                .title("Recordatorio")
                .body("Tu cita es mañana")
                .build();

        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

        channel.send(push, providerMock);

        verify(providerMock).send(
                tokenCaptor.capture(),
                titleCaptor.capture(),
                bodyCaptor.capture()
        );

        assertEquals("AbCdEfGhIjKlMnOpQrStUvWxYz123456", tokenCaptor.getValue());
        assertEquals("Recordatorio", titleCaptor.getValue());
        assertEquals("Tu cita es mañana", bodyCaptor.getValue());
    }

    @Test
    @DisplayName("Debe rechazar device token null")
    void shouldRejectNullDeviceToken() {
        PushNotification push = new PushNotification.Builder()
                .token(null)
                .title("Test")
                .body("Test body")
                .build();

        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> channel.send(push, providerMock)
        );

        verifyNoInteractions(providerMock);
    }

    @Test
    @DisplayName("Debe rechazar device token vacío")
    void shouldRejectEmptyDeviceToken() {
        PushNotification push = new PushNotification.Builder()
                .token("")
                .title("Test")
                .body("Test body")
                .build();

        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> channel.send(push, providerMock)
        );

        verifyNoInteractions(providerMock);
    }

    @Test
    @DisplayName("Debe rechazar título null")
    void shouldRejectNullTitle() {
        PushNotification push = new PushNotification.Builder()
                .token("AbCdEfGhIjKlMnOpQrStUvWxYz123456")
                .title(null)
                .body("Test body")
                .build();

        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> channel.send(push, providerMock)
        );

        verifyNoInteractions(providerMock);
    }

    @Test
    @DisplayName("Debe permitir body null")
    void shouldAllowNullBody() {
        PushNotification push = new PushNotification.Builder()
                .token("AbCdEfGhIjKlMnOpQrStUvWxYz123456")
                .title("Nueva notificación")
                // No llamamos a .body()
                .build();

        assertDoesNotThrow(() -> channel.send(push, providerMock));

        verify(providerMock).send(
                "AbCdEfGhIjKlMnOpQrStUvWxYz123456",
                "Nueva notificación",
                null
        );
    }
}