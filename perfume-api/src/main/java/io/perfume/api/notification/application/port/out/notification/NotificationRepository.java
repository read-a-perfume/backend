package io.perfume.api.notification.application.port.out.notification;

import io.perfume.api.notification.domain.Notification;

public interface NotificationRepository {

  Notification save(Notification notification);
}
