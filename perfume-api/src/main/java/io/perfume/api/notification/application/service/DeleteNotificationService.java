package io.perfume.api.notification.application.service;

import io.perfume.api.notification.application.exception.DeleteNotificationPermissionDeniedException;
import io.perfume.api.notification.application.exception.NotFoundNotificationException;
import io.perfume.api.notification.application.port.in.DeleteNotificationUseCase;
import io.perfume.api.notification.application.port.out.notification.NotificationQueryRepository;
import io.perfume.api.notification.application.port.out.notification.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeleteNotificationService implements DeleteNotificationUseCase {

  private final NotificationQueryRepository notificationQueryRepository;

  private final NotificationRepository notificationRepository;

  public DeleteNotificationService(
      NotificationQueryRepository notificationQueryRepository,
      NotificationRepository notificationRepository) {
    this.notificationQueryRepository = notificationQueryRepository;
    this.notificationRepository = notificationRepository;
  }

  @Override
  @Transactional
  public void delete(Long userId, Long notificationId, LocalDateTime now) {
    var notification =
        notificationQueryRepository
            .findById(notificationId)
            .orElseThrow(() -> new NotFoundNotificationException(notificationId));

    if (!notification.isOwner(userId)) {
      throw new DeleteNotificationPermissionDeniedException(userId, notificationId);
    }

    notification.markDelete(now);
    notificationRepository.save(notification);
  }
}
