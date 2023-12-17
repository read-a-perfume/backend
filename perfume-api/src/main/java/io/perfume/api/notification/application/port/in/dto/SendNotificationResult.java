package io.perfume.api.notification.application.port.in.dto;

import io.perfume.api.notification.domain.NotificationType;

public record SendNotificationResult(
    Long notificationId, String content, String redirectUrl, NotificationType notificationType) {
  public static SendNotificationResult from(NotificationResult notificationResult) {
    return new SendNotificationResult(
        notificationResult.id(),
        notificationResult.content(),
        notificationResult.redirectUrl(),
        notificationResult.notificationType());
  }
}
