package br.com.picpay.service;

import br.com.picpay.exception.DefaultException;
import br.com.picpay.web.client.NotificationRestClient;
import br.com.picpay.entity.dto.NotificationEventDto;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class NotificationRabbitmqConsumer {

    @RestClient
    @Inject
    NotificationRestClient notificationRestClient;

    private int currentRetries = 0;

    @Incoming("transference-notification")
    @Blocking
    public void process(NotificationEventDto event) {
        Log.info("Event received from broker");
        Log.info("Processing...");

        int maxRetries = 8;
        try(Response response = notificationRestClient.triggerNotification(event)) {
            if(response.getStatus() == 204) {
                Log.info("Sent notification successfully");
                Log.info(event.toString());
                resetCurrentRetries();
            } else {
                Log.error("Notification service unavailable at the time");
                currentRetries++;

                if(currentRetries > maxRetries) {
                    Log.error("Maximum number of retries exceeded");
                    resetCurrentRetries();
                    throw new DefaultException("Notification service unavailable, max retries exceeded");
                }
                Log.info("Retrying...");
                Thread.sleep(100);
                process(event);
            }
        } catch (InterruptedException e) {
            throw new DefaultException("Thread interrupted.");
        }
    }

    private void resetCurrentRetries() {
        this.currentRetries = 0;
    }
}
