package io.perfume.api.mypage.adapter.port.out.persistence.follow.mapper;

import io.perfume.api.mypage.adapter.port.out.persistence.follow.FollowJpaEntity;
import io.perfume.api.mypage.domain.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

  public Follow toDomain(FollowJpaEntity followJpaEntity) {
    return new Follow(
        followJpaEntity.getId(),
        followJpaEntity.getFollowerId(),
        followJpaEntity.getFollowingId(),
        followJpaEntity.getCreatedAt(),
        followJpaEntity.getUpdatedAt(),
        followJpaEntity.getDeletedAt());
  }

  public FollowJpaEntity toEntity(Follow follow) {
    return new FollowJpaEntity(
        follow.getId(),
        follow.getFollowerId(),
        follow.getFollowingId(),
        follow.getCreatedAt(),
        follow.getUpdatedAt(),
        follow.getDeletedAt()
    );
  }
}
