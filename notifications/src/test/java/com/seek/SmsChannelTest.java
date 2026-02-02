package com.seek;

import com.seek.channel.SmsChannel;
import com.seek.core.Notification;
import com.seek.core.NotificationException;
import com.seek.model.SmsNotification;
import com.seek.provider.SmsProvider;
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
@DisplayName("SmsChannel")
class SmsChannelTest {

    @Mock
    private SmsProvider providerMock;

    @Mock
    private Notification genericNotificationMock;

    private SmsChannel channel;

    @BeforeEach
    void setUp() {
        channel = new SmsChannel();
    }

    @Test
    @DisplayName("Debe enviar SmsNotification correctamente")
    void shouldSendSmsNotification() {
        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber("+5491112345678")
                .message("Tu código es: 123456")
                .build();

        channel.send(sms, providerMock);

        verify(providerMock, times(1)).send(
                "+5491112345678",
                "Tu código es: 123456"
        );
    }

    @Test
    @DisplayName("Debe pasar los parámetros correctos al provider")
    void shouldPassCorrectParametersToProvider() {
        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber("+5491187654321")
                .message("Bienvenido a nuestro servicio")
                .build();

        ArgumentCaptor<String> phoneCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        channel.send(sms, providerMock);

        verify(providerMock).send(
                phoneCaptor.capture(),
                messageCaptor.capture()
        );

        assertEquals("+5491187654321", phoneCaptor.getValue());
        assertEquals("Bienvenido a nuestro servicio", messageCaptor.getValue());
    }

    @Test
    @DisplayName("Debe rechazar número de teléfono null")
    void shouldRejectNullPhoneNumber() {
        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber(null)
                .message("Test message")
                .build();

        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> channel.send(sms, providerMock)
        );

        verifyNoInteractions(providerMock);
    }

    @Test
    @DisplayName("Debe rechazar número de teléfono vacío")
    void shouldRejectEmptyPhoneNumber() {
        // Arrange
        SmsNotification sms = new SmsNotification.Builder()
                .phoneNumber("   ")
                .message("Test message")
                .build();

        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> channel.send(sms, providerMock)
        );

        verifyNoInteractions(providerMock);
    }
}