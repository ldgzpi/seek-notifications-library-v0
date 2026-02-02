package com.seek;

import com.seek.channel.EmailChannel;
import com.seek.core.Notification;
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
@DisplayName("EmailChannel")
class EmailChannelTest {

    @Mock
    private EmailProvider providerMock;

    @Mock
    private Notification genericNotificationMock;

    private EmailChannel channel;

    @BeforeEach
    void setUp() {
        channel = new EmailChannel();
    }

    @Test
    @DisplayName("TEST #6: Debe enviar EmailNotification correctamente")
    void shouldSendEmailNotification() {

        EmailNotification email = new EmailNotification.Builder()
                .to("recipient@test.com")
                .subject("Test Subject")
                .content("Test Content")
                .build();

        channel.send(email, providerMock);

        verify(providerMock, times(1)).send(
                null,
                "recipient@test.com",
                "Test Subject",
                "Test Content"
        );
    }

    @Test
    @DisplayName("TEST #7: Debe pasar los parámetros correctos al provider")
    void shouldPassCorrectParametersToProvider() {
        EmailNotification email = new EmailNotification.Builder()
                .to("john@example.com")
                .subject("Meeting Reminder")
                .content("<h1>Important Meeting</h1>")
                .build();

        ArgumentCaptor<String> toCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);

        channel.send(email, providerMock);

        verify(providerMock).send(
                isNull(),
                toCaptor.capture(),
                subjectCaptor.capture(),
                contentCaptor.capture()
        );

        assertEquals("john@example.com", toCaptor.getValue(),
                "El destinatario debe ser john@example.com");
        assertEquals("Meeting Reminder", subjectCaptor.getValue(),
                "El subject debe ser Meeting Reminder");
        assertEquals("<h1>Important Meeting</h1>", contentCaptor.getValue(),
                "El contenido debe incluir el HTML");
    }
}