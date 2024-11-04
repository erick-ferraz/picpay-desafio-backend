package br.com.picpay.exception;

import br.com.picpay.entity.dto.ApiErrorDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class InsufficientBalanceException extends DefaultException {

    private String message;

    public InsufficientBalanceException(String message) {
        this.message = message;
    }

    @Override
    public ApiErrorDto toApiErrorModel() {
        return new ApiErrorDto(
            HttpResponseStatus.UNPROCESSABLE_ENTITY.code(),
            LocalDateTime.now(),
            "Insufficient Balance",
            message,
            Collections.emptyMap()
        );
    }
}
