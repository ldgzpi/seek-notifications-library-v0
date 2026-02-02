# Seek Notification Library - Guía de Extensión

## Recordamos la arquitectura

La librería sigue el flujo Sender -> Dispatcher -> Channel -> Provider:

Sender: Centro de notificaciones. Es la interfaz con la que se espera que el usuario interactue.

Dispatcher: Recibe parámetros y los convierte en objetos según la notificación que se desee enviar.

Channel: Contiene toda la lógica de negocio y validaciones.

Provider: Realiza la conexión HTTP real con el proveedor externo.

## Agregar un nuevo proveedor
*Ejemplo: Queremos agregar soporte para AWS SES.*

Solo necesitas implementar la interfaz `EmailProvider`.

1.  Crea la clase `AwsSesProvider` en `com.seek.provider`.
2.  Implementa `getName()` (para logs) y `send()` (la lógica).
3.  **Importante:** Usa `NotificationException` para envolver cualquier error de la SDK de AWS.

```java
public class AwsSesProvider implements EmailProvider {
    @Override
    public void send(String from, String to, String subject, String body) {
        try {
            // Lógica de AWS SDK...
        } catch (AwsServiceException e) {
            throw NotificationException.providerError("AWS SES", e);
        }
    }
}
```

## Agregar un nuevo canal
Esto requiere tocar las 4 capas de la arquitectura en el siguiente orden:

# 1. Model
Se debe crear un archivo com.seek.model.SmsNotification, que extienda de BaseNotification.
Debe tener los campos especificos del nuevo objeto que se desea representar. En este caso, para un
canal SMS, podría ser el número de teléfono.
Define qué datos necesita un SMS.

# 2. Provider
Se debe crear un archivo com.seek.provider.SmsProvider que implemente el método send.
Luego, la interfaz com.seek.provider.SmsProvider será implementada por cada provider de SMS en específico,
con las particularidades de cada uno.

# 3. Channel
Se debe crear un archivo com.seek.channel.SmsChannel que implemente la interfaz Implementa Channel<SmsProvider>.
El mismo contendrá toda la lógica de negocio y validaciones. Es importante definir el tipo de provider a recibir
en la firma del método.

# 4. Dispatcher
Se debe crear el archivo com.seek.client.dispatcher.SmsDispatcher, que implementará el método send
que construye la notificación a enviar y que luego internamente delega el envío en el provider.
La función send del dispatcher constituirá el punto de contacto a través del cual el desarrollador utilizará la librería.

# 5. Notification Sender
Finalmente, el nuevo canal deberá ser incluido en el NotificationSender para poder ser accesible.
```java
// En NotificationSender.java
public SmsDispatcher sms(SmsProvider provider) {
return new SmsDispatcher(this, provider);
}
```