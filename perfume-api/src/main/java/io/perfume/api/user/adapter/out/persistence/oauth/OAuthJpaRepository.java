package io.perfume.api.user.adapter.out.persistence.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

interface OAuthJpaRepository extends JpaRepository<OAuthJpaEntity, Long> {
}
