package br.com.picpay.exception;

import br.com.picpay.entity.dto.ApiErrorDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class UnauthorizedTransferException extends DefaultException {

    private String message;

    public UnauthorizedTransferException(String message) {
        this.message = message;
    }

    @Override
    public ApiErrorDto toApiErrorModel() {
        return new ApiErrorDto(
            HttpResponseStatus.UNAUTHORIZED.code(),
            LocalDateTime.now(),
            "Unauthorized",
            message,
            Collections.emptyMap()
        );
    }
}
