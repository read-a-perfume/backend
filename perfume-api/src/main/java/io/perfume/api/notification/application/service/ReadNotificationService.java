package io.perfume.api.notification.application.service;

import io.perfume.api.notification.application.exception.NotFoundNotificationException;
import io.perfume.api.notification.application.exception.ReadNotificationPermissionDeniedException;
import io.perfume.api.notification.application.port.in.ReadNotificationUseCase;
import io.perfume.api.notification.application.port.out.notification.NotificationQueryRepository;
import io.perfume.api.notification.application.port.out.notification.NotificationRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadNotificationService implements ReadNotificationUseCase {

  private final NotificationQueryRepository notificationQueryRepository;

  private final NotificationRepository notificationRepository;

  public ReadNotificationService(
      NotificationQueryRepository notificationQueryRepository,
      NotificationRepository notificationRepository) {
    this.notificationQueryRepository = notificationQueryRepository;
    this.notificationRepository = notificationRepository;
  }

  @Override
  @Transactional
  public void read(Long userId, Long notificationId, LocalDateTime now) {
    var notification =
        notificationQueryRepository
            .findById(notificationId)
            .orElseThrow(() -> new NotFoundNotificationException(notificationId));

    if (!notification.isOwner(userId)) {
      throw new ReadNotificationPermissionDeniedException(userId, notificationId);
    }

    notification.readNotification();
    notificationRepository.save(notification);
  }
}
