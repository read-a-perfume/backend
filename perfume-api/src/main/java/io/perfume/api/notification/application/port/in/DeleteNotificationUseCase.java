package io.perfume.api.notification.application.port.in;

import java.time.LocalDateTime;

public interface DeleteNotificationUseCase {

  void delete(Long userId, Long notificationId, LocalDateTime now);
}
