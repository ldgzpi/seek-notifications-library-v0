# Seek Notification Library

Librería Java destinada al envío de notificaciones multicanal. 

## Características

* **Customizable:** Acepta diversos proveedores para los distintos canales.
* **Type-Safe:** No permite asignar proveedores a canales que no sean compatibles.
* **Fluent API:** Interfaz de lectura natural.

## Instalación

### Paso 1: Clonar y Empaquetar

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/seek-notifications-library.git
cd seek-notifications-library/notifications

# Compilar y empaquetar (genera el .jar)
mvn clean package

# El JAR estará en: target/notifications-1.0.jar
```

### Paso 2: Instalar en tu Repositorio Local de Maven

```bash
# Instalar en ~/.m2/repository
mvn clean install
```

### Paso 3: Agregar como Dependencia

Agrega esto a tu `pom.xml`:

```xml
<dependency>
    <groupId>com.seek</groupId>
    <artifactId>notifications</artifactId>
    <version>1.0</version>
</dependency>
```

O copia manualmente el JAR:

```xml
<dependency>
    <groupId>com.seek</groupId>
    <artifactId>notifications</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/notifications-1.0.jar</systemPath>
</dependency>
```
---

## Arquitectura

La librería sigue el flujo Sender -> Dispatcher -> Channel -> Provider:

Sender: Centro de notificaciones. Es la interfaz con la que se espera que el usuario interactue.

Dispatcher: Recibe parámetros y los convierte en objetos según la notificación que se desee enviar.

Channel: Contiene toda la lógica de negocio y validaciones.

Provider: Realiza la conexión HTTP real con el proveedor externo.

---

## Quick Start

### 1️⃣ Email con SendGrid

```java
import com.seek.core.NotificationSender;
import com.seek.core.NotificationException;
import com.seek.provider.SendGridProvider;

public class EmailExample {
    public static void main(String[] args) {
        // 1. Crear el sender (recomendado como Singleton)
        NotificationSender sender = new NotificationSender();
        
        // 2. Configurar provider con tu API key
        var sendgrid = new SendGridProvider(
            "SG.YOUR_API_KEY",
            "no-reply@myapp.com"  // Email remitente por defecto
        );
        
        // 3. Enviar
        try {
            sender.email(sendgrid).send(
                "usuario@ejemplo.com",      // To
                "¡Bienvenido!",              // Subject
                "<h1>Hola Mundo</h1>"       // Content (HTML)
            );
            
            System.out.println("Email enviado!");
            
        } catch (NotificationException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Código: " + e.getErrorCode());
        }
    }
}
```

### 2️⃣ SMS con Twilio

```java
import com.seek.provider.TwilioProvider;

// Configurar Twilio
var twilio = new TwilioProvider(
    "AC1234567890abcdef",  // Account SID
    "your-auth-token"
);

// Enviar SMS
try {
    sender.sms(twilio).send(
        "+5491112345678",           // Número (formato internacional)
        "Tu código es: 123456"      // Mensaje (máx 160 caracteres)
    );
    
    System.out.println("SMS enviado!");
    
} catch (NotificationException e) {
    System.err.println(e.getMessage());
}
```

### 3️⃣ Push con Firebase

```java
import com.seek.provider.FirebaseProvider;

// Configurar Firebase
var firebase = new FirebaseProvider("my-firebase-project");

// Enviar push
try {
    sender.push(firebase).send(
        "device-token-abc123...",   // Device token
        "Nueva oferta",             // Título
        "50% de descuento"          // Cuerpo
    );
    
    System.out.println("Push enviado!");
    
} catch (NotificationException e) {
    System.err.println(e.getMessage());
}
```
---

## Manejo de Errores

La librería usa `NotificationException` con códigos de error:

```java
try {
    sender.email(provider).send(...);
    
} catch (NotificationException e) {
    switch (e.getErrorCode()) {
        case "VAL-002":
            // Error de validación (email inválido, campo vacío, etc.)
            System.out.println("Corrige estos campos: " + e.getMessage());
            break;
            
        case "NET-004":
            // Error de red (timeout, sin internet, etc.)
            System.out.println("Reintentando en 5 segundos...");
            break;
            
        case "PRV-401":
        case "PRV-403":
            // API key inválida o sin permisos
            System.out.println("Revisa tu API key");
            break;
            
        default:
            System.out.println("Error desconocido: " + e.getMessage());
    }
}
```

### Códigos de Error

| Código | Tipo | Descripción |
|--------|------|-------------|
| `VAL-002` | Validación | Email inválido, campo vacío, formato incorrecto |
| `NET-004` | Red | Timeout, sin conexión, DNS error |
| `PRV-4xx` | Provider | Errores HTTP del proveedor (401, 403, 429, etc.) |
| `PRV-5xx` | Provider | Errores del servidor del proveedor |

---

## Configuración Avanzada
### Email con Remitente Personalizado

```java
// Opción 1: Remitente por defecto al crear el provider
var sendgrid = new SendGridProvider(
    "API_KEY",
    "Marketing-Team",
    "marketing@mycompany.com"
);

// Opción 2: Usar remitente por defecto global
var sendgrid = new SendGridProvider("API_KEY");
// Usará: noreply@notifications.com
```

### Validaciones Manuales

```java
import com.seek.core.NotificationValidator;
import com.seek.model.EmailNotification;

EmailNotification email = new EmailNotification.Builder()
    .to("invalid-email")  // Email inválido
    .subject("Test")
    .content("Content")
    .build();

try {
    NotificationValidator.validateEmail(email);
} catch (NotificationException e) {
    System.out.println("Validación falló: " + e.getMessage());
    // Output: "Invalid email format: invalid-email"
}
```

### Multi-Tenant (Múltiples Cuentas)

```java
// Diferentes equipos con diferentes cuentas de SendGrid
var marketingProvider = new SendGridProvider(
    "SG.MARKETING_KEY",
    "Marketing-Team",
    "marketing@company.com"
);

var transactionalProvider = new SendGridProvider(
    "SG.TRANSACTIONAL_KEY",
    "Transactional-Team",
    "noreply@company.com"
);

// Usar según el contexto
sender.email(marketingProvider).send(...);
sender.email(transactionalProvider).send(...);
```
---
## Documentación Adicional

- **[Guía de Extensión](EXTENDING.md)**: Cómo agregar nuevos providers o canales