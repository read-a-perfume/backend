package io.perfume.api.notification.adapter.port.out;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.notification.application.port.out.notification.NotificationRepository;
import io.perfume.api.notification.domain.Notification;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements NotificationRepository {

  private final NotificationJpaRepository notificationJpaRepository;

  private final NotificationMapper notificationMapper;

  @Override
  public Notification save(Notification notification) {
    NotificationEntity entity =
        notificationJpaRepository.save(notificationMapper.toEntity(notification));
    return notificationMapper.toDomain(entity);
  }
}
