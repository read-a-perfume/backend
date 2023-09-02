package io.perfume.api.user.adapter.out.persistence.social;

import org.springframework.data.jpa.repository.JpaRepository;

interface SocialAccountJpaRepository extends JpaRepository<SocialAccountEntity, Long> {
}
