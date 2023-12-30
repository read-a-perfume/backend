package io.perfume.api.notification.application.port.out.notification;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.notification.application.port.in.dto.NotificationResult;
import io.perfume.api.notification.domain.Notification;

import java.util.Optional;

public interface NotificationQueryRepository {

  Optional<Notification> findById(Long id);

  CursorPagination<Notification> findByreceiveUserId(CursorPageable pageable, long userId);
}
