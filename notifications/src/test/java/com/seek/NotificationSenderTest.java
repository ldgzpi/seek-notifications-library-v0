package com.seek;

import com.seek.channel.Channel;
import com.seek.core.Notification;
import com.seek.core.NotificationException;
import com.seek.core.NotificationSender;
import com.seek.provider.EmailProvider;
import com.seek.provider.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificationSender")
class NotificationSenderTest {

    @Mock
    private Channel<Provider> channelMock;

    @Mock
    private Provider providerMock;

    @Mock
    private Notification notificationMock;

    private NotificationSender sender;

    @BeforeEach
    void setUp() {
        sender = new NotificationSender();
    }

    @Test
    @DisplayName("TEST #2: Debe delegar el envío al canal correctamente")
    void shouldDelegateToChannel() {
        sender.send(notificationMock, channelMock, providerMock);

        verify(channelMock, times(1)).send(notificationMock, providerMock);
        verify(channelMock, only()).send(any(), any());
    }

    @Test
    @DisplayName("TEST #3: Debe rechazar notificación nula")
    void shouldRejectNullNotification() {
        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> sender.send(null, channelMock, providerMock)
        );

        verifyNoInteractions(channelMock);
    }

    @Test
    @DisplayName("TEST #4: Debe crear EmailDispatcher con provider válido")
    void shouldCreateEmailDispatcherWithValidProvider() {
        EmailProvider emailProviderMock = mock(EmailProvider.class);
        var dispatcher = sender.email(emailProviderMock);

        assertNotNull(dispatcher, "El dispatcher no debe ser null");

        assertInstanceOf(com.seek.dispatcher.EmailDispatcher.class, dispatcher,
                "Debe retornar un EmailDispatcher");
    }

    @Test
    @DisplayName("TEST #5: Debe rechazar EmailProvider nulo")
    void shouldRejectNullEmailProvider() {
        NotificationException exception = assertThrows(
                NotificationException.class,
                () -> sender.email(null)
        );
    }
}