package br.com.picpay.entity.dto;

public record NotificationEventDto(String message,
                                   TransferenceNotificationDto transference) {
}
