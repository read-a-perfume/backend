package io.perfume.api.notification.application.port.in;

import dto.repository.CursorPagination;
import io.perfume.api.notification.application.port.in.dto.GetNotificationCommand;
import io.perfume.api.notification.application.port.in.dto.GetNotificationResult;

public interface GetNotificationUseCase {

  CursorPagination<GetNotificationResult> getNotifications(
      long userId, GetNotificationCommand command);
}
