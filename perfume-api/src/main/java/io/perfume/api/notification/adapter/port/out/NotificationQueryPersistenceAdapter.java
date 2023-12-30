package io.perfume.api.notification.adapter.port.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.notification.application.port.out.notification.NotificationQueryRepository;
import io.perfume.api.notification.domain.Notification;

import java.util.List;
import java.util.Optional;

import static io.perfume.api.notification.adapter.port.out.QNotificationEntity.notificationEntity;

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

  @Override
  public CursorPagination<Notification> findByreceiveUserId(CursorPageable pageable, long userId) {
    var qb = jpaQueryFactory
            .selectFrom(notificationEntity)
            .where(notificationEntity.receiveUserId.eq(userId)
                    .and(notificationEntity.deletedAt.isNull()))
            .orderBy(notificationEntity.id.desc());

    final Optional<Long> cursor = pageable.getCursor(Long::parseLong);
    if (cursor.isPresent()) {
      final long id = cursor.get();
      if (pageable.isNext()) {
        qb.where(notificationEntity.receiveUserId.gt(id));
      } else {
        qb.where(notificationEntity.receiveUserId.lt(id));
      }
    }

    final List<Notification> notifications =
            qb.limit(pageable.getFetchSize()).fetch().stream()
            .map(notificationMapper::toDomain)
            .toList();

    return CursorPagination.of(notifications, pageable, notification -> notification.getReceiveUserId().toString());
  }
}
