package io.perfume.api.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationKeyJpaRepository extends JpaRepository<AuthenticationKeyJpaEntity, Long> {
}
