package br.com.picpay.exception;

import br.com.picpay.entity.dto.RfcDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class DefaultException extends RuntimeException {

    public RfcDto toRfcModel() {
        return new RfcDto(
            HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
            LocalDateTime.now(),
            "Internal server error",
            "Something went wrong",
            Collections.emptyMap()
        );
    }
}
