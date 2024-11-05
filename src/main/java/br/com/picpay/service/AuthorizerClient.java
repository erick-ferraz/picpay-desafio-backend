package br.com.picpay.service;

import br.com.picpay.web.client.AuthorizerRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AuthorizerClient {

    @RestClient
    @Inject
    AuthorizerRestClient client;

    public Response requestClient() {
        return client.consultAuthorizer();
    }
}
