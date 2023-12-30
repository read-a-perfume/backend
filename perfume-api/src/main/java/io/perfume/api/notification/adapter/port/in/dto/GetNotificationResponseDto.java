package io.perfume.api.notification.adapter.port.in.dto;

import io.perfume.api.notification.application.port.in.dto.GetNotificationResult;

public record GetNotificationResponseDto(Long id, String content, String notificationType) {

    public static GetNotificationResponseDto from(GetNotificationResult getNotificationResult) {
        return new GetNotificationResponseDto(
                getNotificationResult.id(),
                getNotificationResult.content(),
                getNotificationResult.notificationType()
        );
    }
}
