package br.com.picpay.controller.mapper;

import br.com.picpay.exception.AlreadyRegisteredDataException;
import br.com.picpay.exception.DefaultException;
import br.com.picpay.exception.InputValidationException;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class RestExceptionMapper {

    @ServerExceptionMapper(DefaultException.class)
    public Response toResponse(DefaultException e) {
        return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .entity(e.toRfcModel())
            .build();
    }

    @ServerExceptionMapper(InputValidationException.class)
    public Response toResponse(InputValidationException e) {
        return Response.status(HttpResponseStatus.BAD_REQUEST.code())
            .entity(e.toRfcModel())
            .build();
    }

    @ServerExceptionMapper(AlreadyRegisteredDataException.class)
    public Response toResponse(AlreadyRegisteredDataException e) {
        return Response.status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code())
            .entity(e.toRfcModel())
            .build();
    }
}
