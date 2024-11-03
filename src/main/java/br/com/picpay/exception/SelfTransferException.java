package br.com.picpay.exception;

import br.com.picpay.entity.dto.ApiErrorDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class SelfTransferException extends DefaultException {

    private String message;

    public SelfTransferException(String message) {
        this.message = message;
    }

    @Override
    public ApiErrorDto toApiErrorModel() {
        return new ApiErrorDto(
            HttpResponseStatus.BAD_REQUEST.code(),
            LocalDateTime.now(),
            "Self transfer error",
            message,
            Collections.emptyMap()
        );
    }
}
