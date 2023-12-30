package io.perfume.api.notification.application.service;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.notification.application.port.in.GetNotificationUseCase;
import io.perfume.api.notification.application.port.in.dto.GetNotificationCommand;
import io.perfume.api.notification.application.port.in.dto.GetNotificationResult;
import io.perfume.api.notification.application.port.out.notification.NotificationQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetNotificationService implements GetNotificationUseCase {

  private final NotificationQueryRepository notificationQueryRepository;

  public GetNotificationService(NotificationQueryRepository notificationQueryRepository) {
    this.notificationQueryRepository = notificationQueryRepository;
  }

  @Override
  public CursorPagination<GetNotificationResult> getNotifications(
      long userId, GetNotificationCommand command) {
    var pageable =
        new CursorPageable(command.pageSize(), command.getDirection(), command.getCursor());
    var notifications = notificationQueryRepository.findByreceiveUserId(pageable, userId);
    var result =
        notifications.getItems().stream()
            .map(
                item ->
                    new GetNotificationResult(
                        item.getId(), item.getContent(), item.getNotificationType().getType()))
            .toList();

    return CursorPagination.of(result, notifications);
  }
}
