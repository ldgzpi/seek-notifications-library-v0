package com.seek;

import com.seek.provider.Provider;
import com.seek.provider.SmsProvider;
import com.seek.provider.TwilioProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TwilioProvider")
class TwilioProviderTest {

    @Test
    @DisplayName("Debe crear provider con accountSid")
    void shouldCreateProviderWithAccountSid() {
        TwilioProvider provider = new TwilioProvider("AC1234567890abcdef", "auth-token-123");

        assertNotNull(provider);
        assertEquals("Twilio", provider.getName());
        assertEquals("AC1234567890abcdef", provider.getAccount());
    }

    @Test
    @DisplayName("Debe implementar las interfaces correctas")
    void shouldImplementCorrectInterfaces() {
        TwilioProvider provider = new TwilioProvider("AC1234567890abcdef", "auth-token-123");

        assertInstanceOf(SmsProvider.class, provider);
        assertInstanceOf(Provider.class, provider);
    }

    @Test
    @DisplayName("Debe permitir envío simulado sin errores")
    void shouldAllowSimulatedSendWithoutErrors() {
        TwilioProvider provider = new TwilioProvider("AC1234567890abcdef", "auth-token-123");

        assertDoesNotThrow(() ->
                provider.send("+5491112345678", "Test message")
        );
    }
}