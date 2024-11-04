package br.com.picpay.web.controller;

import br.com.picpay.entity.dto.UpdateWalletParamsDto;
import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.entity.dto.WalletResponseDto;
import br.com.picpay.service.WalletService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/wallets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @POST
    public Response createWallet(WalletRequestDto dto) {
        return Response.ok()
            .entity(service.createWallet(dto))
            .build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok()
            .entity(new WalletResponseDto(service.getById(id)))
            .build();
    }

    @GET
    public Response getAll() {
        return Response.ok()
            .entity(service.getAll())
            .build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id,
                               @BeanParam UpdateWalletParamsDto dto) {
        service.updateById(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }
}
