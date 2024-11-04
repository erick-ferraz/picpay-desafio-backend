package br.com.picpay.service;

import br.com.picpay.entity.dto.NotificationEventDto;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class NotificationRabbitmqProducer {

    @Channel("transference-notification")
    @Broadcast
    Emitter<NotificationEventDto> emitter;

    public void sendNotification(NotificationEventDto notification) {
        emitter.send(notification);
        Log.info("Evento de notificacao enviado para fila");
    }
}
