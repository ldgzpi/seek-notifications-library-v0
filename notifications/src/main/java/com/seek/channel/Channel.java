package com.seek.channel;

import com.seek.core.Notification;
import com.seek.model.EmailNotification;
import com.seek.provider.*;

// T extends Provider: Esto es CLAVE.
// Obligamos a que cada canal defina con qué tipo de proveedor trabaja.
public interface Channel<T extends Provider> {
    
    // Recibe la notificación genérica y el proveedor específico T
    void send(Notification notification, T provider);
}