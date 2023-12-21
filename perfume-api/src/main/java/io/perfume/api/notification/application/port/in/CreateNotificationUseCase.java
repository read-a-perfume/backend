package io.perfume.api.notification.application.port.in;

import io.perfume.api.notification.application.port.in.dto.CreateNotificationCommand;
import io.perfume.api.notification.application.port.in.dto.NotificationResult;

public interface CreateNotificationUseCase {

  NotificationResult create(CreateNotificationCommand command);
}
