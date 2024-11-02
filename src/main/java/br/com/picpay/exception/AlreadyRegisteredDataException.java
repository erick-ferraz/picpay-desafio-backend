package br.com.picpay.exception;

import br.com.picpay.entity.dto.RfcDto;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.time.LocalDateTime;
import java.util.Collections;

public class AlreadyRegisteredDataException extends DefaultException {

    private String message;

    public AlreadyRegisteredDataException(String message) {
        this.message = message;
    }

    @Override
    public RfcDto toRfcModel() {
        return new RfcDto(
            HttpResponseStatus.UNPROCESSABLE_ENTITY.code(),
            LocalDateTime.now(),
            "Data already registered in database",
            message,
            Collections.emptyMap()
        );
    }
}
