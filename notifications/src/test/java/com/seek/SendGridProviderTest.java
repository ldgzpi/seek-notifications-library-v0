package com.seek;

import com.seek.provider.EmailProvider;
import com.seek.provider.Provider;
import com.seek.provider.SendGridProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SendGridProvider")
class SendGridProviderTest {

    @Test
    @DisplayName("TEST #12: Debe crear provider con API key")
    void shouldCreateProviderWithApiKey() {
        SendGridProvider provider = new SendGridProvider("test-api-key-12345");

        assertNotNull(provider, "El provider no debe ser null");

        assertEquals("SendGrid", provider.getName(),
                "El nombre del provider debe ser SendGrid");

        assertEquals("Default-Account", provider.getAccount(),
                "Debe usar la cuenta por defecto cuando no se especifica");
    }

    @Test
    @DisplayName("TEST #13: Debe crear provider con API key y account personalizado")
    void shouldCreateProviderWithCustomAccount() {
        String apiKey = "custom-api-key-67890";
        String customAccount = "Marketing-Team";

        SendGridProvider provider = new SendGridProvider(apiKey, customAccount, "noreply@notifications.com");

        assertEquals("SendGrid", provider.getName(),
                "El nombre debe ser SendGrid independientemente de la cuenta");

        assertEquals("Marketing-Team", provider.getAccount(),
                "Debe usar el nombre de cuenta personalizado");
    }

    @Test
    @DisplayName("TEST #14: El nombre debe ser constante")
    void nameShouldBeConstant() {
        SendGridProvider provider1 = new SendGridProvider("key1");
        SendGridProvider provider2 = new SendGridProvider("key2", "Account2", "noreply@notifications.com");

        assertEquals(provider1.getName(), provider2.getName(),
                "Todos los SendGridProvider deben tener el mismo nombre");

        assertEquals("SendGrid", provider1.getName(),
                "El nombre debe ser exactamente SendGrid");
    }

    @Test
    @DisplayName("TEST #15: Debe permitir múltiples instancias con diferentes accounts")
    void shouldAllowMultipleInstancesWithDifferentAccounts() {
        // Simulamos un escenario multi-tenant
        SendGridProvider marketing = new SendGridProvider("key1", "Marketing", "noreply@notifications.com");
        SendGridProvider sales = new SendGridProvider("key2", "Sales", "noreply@notifications.com");
        SendGridProvider support = new SendGridProvider("key3", "Support", "noreply@notifications.com");

        assertEquals("Marketing", marketing.getAccount());
        assertEquals("Sales", sales.getAccount());
        assertEquals("Support", support.getAccount());

        assertEquals("SendGrid", marketing.getName());
        assertEquals("SendGrid", sales.getName());
        assertEquals("SendGrid", support.getName());

        assertNotSame(marketing, sales,
                "Deben ser instancias independientes");
        assertNotSame(sales, support,
                "Deben ser instancias independientes");
    }

    @Test
    @DisplayName("TEST #16: Debe implementar las interfaces correctas")
    void shouldImplementCorrectInterfaces() {
        SendGridProvider provider = new SendGridProvider("test-key");
        // Debe ser EmailProvider
        assertInstanceOf(EmailProvider.class, provider,
                "SendGridProvider debe implementar EmailProvider");

        // EmailProvider extiende Provider, así que también debe ser Provider
        assertInstanceOf(Provider.class, provider,
                "SendGridProvider debe ser un Provider");
    }
}