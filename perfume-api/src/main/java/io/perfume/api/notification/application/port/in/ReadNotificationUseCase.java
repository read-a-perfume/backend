package io.perfume.api.notification.application.port.in;

import java.time.LocalDateTime;

public interface ReadNotificationUseCase {

  void read(Long userId, Long notificationId, LocalDateTime now);
}
