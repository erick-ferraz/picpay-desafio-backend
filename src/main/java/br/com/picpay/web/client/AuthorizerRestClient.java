package br.com.picpay.web.client;

import br.com.picpay.entity.dto.AuthorizerDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "authorizer-rest-client")
public interface AuthorizerRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response consultAuthorizer();
}
