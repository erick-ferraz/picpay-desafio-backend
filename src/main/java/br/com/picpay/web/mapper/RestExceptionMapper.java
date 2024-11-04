package br.com.picpay.web.mapper;

import br.com.picpay.exception.*;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class RestExceptionMapper {

    @ServerExceptionMapper(DefaultException.class)
    public Response toResponse(DefaultException e) {
        return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(InputValidationException.class)
    public Response toResponse(InputValidationException e) {
        return Response.status(HttpResponseStatus.BAD_REQUEST.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(AlreadyRegisteredDataException.class)
    public Response toResponse(AlreadyRegisteredDataException e) {
        return Response.status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(NonExistentWalletException.class)
    public Response toResponse(NonExistentWalletException e) {
        return Response.status(HttpResponseStatus.NOT_FOUND.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(OwnerTypeException.class)
    public Response toResponse(OwnerTypeException e) {
        return Response.status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(InsufficientBalanceException.class)
    public Response toResponse(InsufficientBalanceException e) {
        return Response.status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(UnauthorizedTransferException.class)
    public Response toResponse(UnauthorizedTransferException e) {
        return Response.status(HttpResponseStatus.UNAUTHORIZED.code())
            .entity(e.toApiErrorModel())
            .build();
    }

    @ServerExceptionMapper(NonExistentTransferenceException.class)
    public Response toResponse(NonExistentTransferenceException e) {
        return Response.status(HttpResponseStatus.NOT_FOUND.code())
            .entity(e.toApiErrorModel())
            .build();
    }
}
