package io.perfume.api.notification.adapter.port.out;

import org.springframework.data.repository.CrudRepository;

public interface NotificationJpaRepository extends CrudRepository<NotificationEntity, Long> {}
