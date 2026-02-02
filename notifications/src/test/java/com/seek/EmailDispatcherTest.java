package com.seek;

import com.seek.core.NotificationSender;
import com.seek.core.Notification;
import com.seek.dispatcher.EmailDispatcher;
import com.seek.model.EmailNotification;
import com.seek.provider.EmailProvider;
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
@DisplayName("EmailDispatcher")
class EmailDispatcherTest {

    @Mock
    private NotificationSender senderMock;

    @Mock
    private EmailProvider providerMock;

    private EmailDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        dispatcher = new EmailDispatcher(senderMock, providerMock);
    }

    @Test
    @DisplayName("TEST #8: Debe construir EmailNotification y delegar al sender")
    void shouldBuildNotificationAndDelegateToSender() {
        dispatcher.send(
                "recipient@test.com",
                "Test Subject",
                "Test Content"
        );

        verify(senderMock, times(1)).send(
                any(Notification.class),
                any(),
                eq(providerMock)
        );
    }

    @Test
    @DisplayName("TEST #9: Debe construir EmailNotification con los parámetros correctos")
    void shouldBuildNotificationWithCorrectParameters() {
        String expectedTo = "john@example.com";
        String expectedSubject = "Important Update";
        String expectedContent = "<h1>Hello World</h1>";

        ArgumentCaptor<Notification> notificationCaptor =
                ArgumentCaptor.forClass(Notification.class);

        dispatcher.send(expectedTo, expectedSubject, expectedContent);

        verify(senderMock).send(
                notificationCaptor.capture(),
                any(),
                any()
        );

        Notification capturedNotification = notificationCaptor.getValue();

        assertInstanceOf(EmailNotification.class, capturedNotification,
                "Debe construir un EmailNotification");

        EmailNotification emailNotification = (EmailNotification) capturedNotification;

        assertEquals(expectedTo, emailNotification.getTo(),
                "El destinatario debe coincidir");
        assertEquals(expectedSubject, emailNotification.getSubject(),
                "El subject debe coincidir");
        assertEquals(expectedContent, emailNotification.getContent(),
                "El contenido debe coincidir");
    }

    @Test
    @DisplayName("TEST #10: Debe usar el provider proporcionado en el constructor")
    void shouldUseProvidedProvider() {
        dispatcher.send("test@test.com", "Subject", "Content");

        verify(senderMock).send(
                any(),
                any(),
                eq(providerMock)
        );
    }

    @Test
    @DisplayName("TEST #11: Debe permitir múltiples envíos consecutivos")
    void shouldAllowMultipleSends() {
        dispatcher.send("user1@test.com", "Subject 1", "Content 1");
        dispatcher.send("user2@test.com", "Subject 2", "Content 2");
        dispatcher.send("user3@test.com", "Subject 3", "Content 3");

        verify(senderMock, times(3)).send(any(), any(), any());
    }
}