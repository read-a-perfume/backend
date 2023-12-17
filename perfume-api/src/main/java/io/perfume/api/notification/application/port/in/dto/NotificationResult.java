package io.perfume.api.notification.application.port.in.dto;

import io.perfume.api.notification.domain.Notification;
import io.perfume.api.notification.domain.NotificationType;

public record NotificationResult(
    Long id,
    String content,
    String redirectUrl,
    Long receiveUserId,
    NotificationType notificationType) {
  public static NotificationResult from(Notification notification) {
    return new NotificationResult(
        notification.getId(),
        notification.getContent(),
        notification.getRedirectUrl(),
        notification.getReceiveUserId(),
        notification.getNotificationType());
  }
}
