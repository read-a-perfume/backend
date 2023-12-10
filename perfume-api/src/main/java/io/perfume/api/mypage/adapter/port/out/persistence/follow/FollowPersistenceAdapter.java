package io.perfume.api.mypage.adapter.port.out.persistence.follow;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.mypage.adapter.port.out.persistence.follow.mapper.FollowMapper;
import io.perfume.api.mypage.application.port.out.FollowRepository;
import io.perfume.api.mypage.domain.Follow;

@PersistenceAdapter
public class FollowPersistenceAdapter implements FollowRepository {

  private final FollowJpaRepository followJpaRepository;

  private final FollowMapper followMapper;

  public FollowPersistenceAdapter(
      FollowJpaRepository followJpaRepository, FollowMapper followMapper) {
    this.followJpaRepository = followJpaRepository;
    this.followMapper = followMapper;
  }

  @Override
  public Follow save(Follow follow) {
    FollowJpaEntity followJpaEntity = followMapper.toEntity(follow);
    FollowJpaEntity savedfollowJpaEntity = followJpaRepository.save(followJpaEntity);
    return followMapper.toDomain(savedfollowJpaEntity);
  }
}
