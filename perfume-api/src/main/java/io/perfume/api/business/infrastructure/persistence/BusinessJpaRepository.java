package io.perfume.api.business.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessJpaRepository extends JpaRepository<BusinessJpaEntity, Long> {
}
