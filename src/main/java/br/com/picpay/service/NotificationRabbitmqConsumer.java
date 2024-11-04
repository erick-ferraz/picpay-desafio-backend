package br.com.picpay.service;

import br.com.picpay.client.NotificationRestClient;
import br.com.picpay.entity.dto.NotificationEventDto;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class NotificationRabbitmqConsumer {

    @RestClient
    @Inject
    NotificationRestClient notificationRestClient;

    @Incoming("transference-notification")
    @Blocking
    public void process(NotificationEventDto event) {
        try(Response response = notificationRestClient.triggerNotification(event)) {
            if(response.getStatus() == 204) {
                Log.info("Sent notification successfully");
                Log.info(event.toString());
            } else {
                Log.error("Notification service unavailable at the time");
                Log.info("Retrying...");
                process(event);
            }
        }
    }
}
