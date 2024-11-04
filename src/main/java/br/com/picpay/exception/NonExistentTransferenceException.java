package br.com.picpay.exception;

import br.com.picpay.entity.dto.ApiErrorDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class NonExistentTransferenceException extends DefaultException {

    private String message;

    public NonExistentTransferenceException(String message) {
        this.message = message;
    }

    @Override
    public ApiErrorDto toApiErrorModel() {
        return new ApiErrorDto(
            HttpResponseStatus.NOT_FOUND.code(),
            LocalDateTime.now(),
            "Non existent transference in database",
            message,
            Collections.emptyMap()
        );
    }
}
