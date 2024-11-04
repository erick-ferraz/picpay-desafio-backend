package br.com.picpay.web.client;

import br.com.picpay.entity.dto.NotificationEventDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "notification-rest-client")
public interface NotificationRestClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response triggerNotification(NotificationEventDto notification);
}
