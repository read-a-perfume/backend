package io.perfume.api.notification.application.port.in.dto;

public record GetNotificationResult(Long id, String content, String notificationType) {
}
