package io.perfume.api.notification.adapter.port.out;

import io.perfume.api.notification.domain.Notification;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
  public Notification toDomain(@NotNull NotificationEntity notificationEntity) {
    if (notificationEntity == null) {
      return null;
    }

    return new Notification(
        notificationEntity.getId(),
        notificationEntity.getContent(),
        notificationEntity.getRedirectUrl(),
        notificationEntity.getReceiveUserId(),
        notificationEntity.getNotificationType(),
        notificationEntity.getIsRead(),
        notificationEntity.getCreatedAt(),
        notificationEntity.getUpdatedAt(),
        notificationEntity.getDeletedAt());
  }

  public NotificationEntity toEntity(@NotNull Notification notification) {
    return new NotificationEntity(
        notification.getId(),
        notification.getContent(),
        notification.getRedirectUrl(),
        notification.getReceiveUserId(),
        notification.getNotificationType(),
        notification.getIsRead(),
        notification.getCreatedAt(),
        notification.getUpdatedAt(),
        notification.getDeletedAt());
  }
}
