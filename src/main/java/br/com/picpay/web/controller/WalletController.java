package br.com.picpay.web.controller;

import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.service.WalletService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
