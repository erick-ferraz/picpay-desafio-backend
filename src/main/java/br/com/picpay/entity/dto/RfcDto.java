package br.com.picpay.entity.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record RfcDto (int status,
                      LocalDateTime dateTime,
                      String title,
                      String detail,
                      Map<String, String> invalidFields) {

}
