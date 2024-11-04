package br.com.picpay.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenceNotificationDto(String to,
                                          String from,
                                          BigDecimal amount,
                                          LocalDateTime date) {
}
