package br.com.picpay.exception;

import br.com.picpay.entity.dto.RfcDto;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.time.LocalDateTime;
import java.util.Map;

public class InputValidationException extends DefaultException {

    private final String message;
    private final Map<String, String> errors;

    public InputValidationException(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    @Override
    public RfcDto toRfcModel() {
        return new RfcDto(
            HttpResponseStatus.BAD_REQUEST.code(),
            LocalDateTime.now(),
            "Input validation failed",
            message,
            errors
        );
    }
}
