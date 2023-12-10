package io.perfume.api.user.adapter.out.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<UserEntity, Long> {}
