package br.com.picpay.exception;

import br.com.picpay.entity.dto.ApiErrorDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class OwnerTypeException extends DefaultException {

    private String message;

    public OwnerTypeException(String message) {
        this.message = message;
    }

    @Override
    public ApiErrorDto toApiErrorModel() {
        return new ApiErrorDto(
            HttpResponseStatus.UNPROCESSABLE_ENTITY.code(),
            LocalDateTime.now(),
            "Action not allowed due owner type",
            message,
            Collections.emptyMap()
        );
    }
}
