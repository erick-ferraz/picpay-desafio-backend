package br.com.picpay.web.controller;

import br.com.picpay.entity.dto.TransferenceRequestDto;
import br.com.picpay.service.TransferenceService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transference")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferenceController {

    private final TransferenceService service;

    public TransferenceController(TransferenceService service) {
        this.service = service;
    }

    @POST
    @Path("/transfer")
    public Response transfer(TransferenceRequestDto dto) {
        return Response.ok()
            .entity(service.transfer(dto))
            .build();
    }

    @GET
    @Path("/{id}")
    public Response getSingleTransferenceById(@PathParam("id") Long id) {
        return Response.ok()
            .entity(service.getById(id))
            .build();
    }

    @GET
    @Path("/wallet/sent/{id}")
    public Response listAllTransfersSentByWalletId(@PathParam("id") Long walletId) {
        return Response.ok()
            .entity(service.listAllTransfersSentByWalletId(walletId))
            .build();
    }

    @GET
    @Path("/wallet/received/{id}")
    public Response listAllTransfersReceivedByWalletId(@PathParam("id") Long walletId) {
        return Response.ok()
            .entity(service.listAllTransfersReceivedByWalletId(walletId))
            .build();
    }
}
