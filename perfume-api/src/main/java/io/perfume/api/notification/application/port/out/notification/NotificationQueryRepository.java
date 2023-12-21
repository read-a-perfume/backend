package io.perfume.api.notification.application.port.out.notification;

import io.perfume.api.notification.domain.Notification;
import java.util.Optional;

public interface NotificationQueryRepository {

  Optional<Notification> findById(Long id);
}
