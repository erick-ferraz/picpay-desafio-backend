package br.com.picpay.exception;

import br.com.picpay.entity.dto.ApiErrorDto;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.time.LocalDateTime;
import java.util.Collections;

public class AlreadyRegisteredDataException extends DefaultException {

    private String message;

    public AlreadyRegisteredDataException(String message) {
        this.message = message;
    }

    @Override
    public ApiErrorDto toApiErrorModel() {
        return new ApiErrorDto(
            HttpResponseStatus.UNPROCESSABLE_ENTITY.code(),
            LocalDateTime.now(),
            "Data already registered in database",
            message,
            Collections.emptyMap()
        );
    }
}
