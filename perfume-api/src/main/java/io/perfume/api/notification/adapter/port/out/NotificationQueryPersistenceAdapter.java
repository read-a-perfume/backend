package io.perfume.api.notification.adapter.port.out;

import static io.perfume.api.notification.adapter.port.out.QNotificationEntity.notificationEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.notification.application.port.out.notification.NotificationQueryRepository;
import io.perfume.api.notification.domain.Notification;
import java.util.Optional;

@PersistenceAdapter
public class NotificationQueryPersistenceAdapter implements NotificationQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final NotificationMapper notificationMapper;

  public NotificationQueryPersistenceAdapter(
      JPAQueryFactory jpaQueryFactory, NotificationMapper notificationMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.notificationMapper = notificationMapper;
  }

  @Override
  public Optional<Notification> findById(Long id) {
    NotificationEntity entity =
        jpaQueryFactory
            .selectFrom(notificationEntity)
            .where(notificationEntity.id.eq(id).and(notificationEntity.deletedAt.isNull()))
            .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }
    return Optional.of(notificationMapper.toDomain(entity));
  }
}
