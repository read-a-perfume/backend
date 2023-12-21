package io.perfume.api.notification.application.service;

import io.perfume.api.notification.application.port.in.CreateNotificationUseCase;
import io.perfume.api.notification.application.port.in.CreateTimeUserIdUseCase;
import io.perfume.api.notification.application.port.in.dto.CreateNotificationCommand;
import io.perfume.api.notification.application.port.in.dto.NotificationResult;
import io.perfume.api.notification.application.port.out.notification.NotificationRepository;
import io.perfume.api.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateNotificationService
    implements CreateNotificationUseCase, CreateTimeUserIdUseCase {

  private final NotificationRepository notificationRepository;

  @Override
  @Transactional
  public NotificationResult create(CreateNotificationCommand command) {
    var notification =
        notificationRepository.save(
            Notification.create(
                command.content(),
                command.redirectUrl(),
                command.receiveUserId(),
                command.notificationType(),
                command.now()));
    return NotificationResult.from(notification);
  }

  @Override
  public String makeTimeIncludeId(Long userId) {
    final String UNDER_BAR = "_";
    return userId + UNDER_BAR + System.currentTimeMillis();
  }
}
