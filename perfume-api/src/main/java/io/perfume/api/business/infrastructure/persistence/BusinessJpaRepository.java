package io.perfume.api.business.infrastructure.persistence;

import io.perfume.api.business.application.port.out.BusinessRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessJpaRepository extends JpaRepository<BusinessJpaEntity, Long>, BusinessRepository {
}
