package br.com.picpay.service;

import br.com.picpay.entity.dto.NotificationEventDto;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class NotificationRabbitmqConsumer {

    @Incoming("transference-notification")
    public void process(NotificationEventDto event) {
        Log.info("Notificacao recebida");
        Log.info(event.toString());
    }
}
