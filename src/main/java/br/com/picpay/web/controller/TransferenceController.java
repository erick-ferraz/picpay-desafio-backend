package br.com.picpay.web.controller;

import br.com.picpay.entity.dto.TransferenceRequestDto;
import br.com.picpay.service.TransferenceService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferenceController {

    private final TransferenceService service;

    public TransferenceController(TransferenceService service) {
        this.service = service;
    }

    @POST
    public Response transfer(TransferenceRequestDto dto) {
        return Response.ok()
            .entity(service.transfer(dto))
            .build();
    }
}
