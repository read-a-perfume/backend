package io.perfume.api.notification.application.port.in.dto;

public record SendNotificationResult(
    Long notificationId, String content, String redirectUrl, String notificationType) {
  public static SendNotificationResult from(NotificationResult notificationResult) {
    return new SendNotificationResult(
        notificationResult.id(),
        notificationResult.content(),
        notificationResult.redirectUrl(),
        notificationResult.notificationType().getType());
  }
}
