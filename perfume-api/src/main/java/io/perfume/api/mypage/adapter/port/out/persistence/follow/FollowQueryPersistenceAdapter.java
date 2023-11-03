package io.perfume.api.mypage.adapter.port.out.persistence.follow;

import static io.perfume.api.mypage.adapter.port.out.persistence.follow.QFollowJpaEntity.followJpaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.mypage.adapter.port.out.persistence.follow.mapper.FollowMapper;
import io.perfume.api.mypage.application.port.out.FollowQueryRepository;
import io.perfume.api.mypage.domain.Follow;
import java.util.Objects;
import java.util.Optional;

@PersistenceAdapter
public class FollowQueryPersistenceAdapter implements FollowQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final FollowMapper followMapper;

  public FollowQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory, FollowMapper followMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.followMapper = followMapper;
  }

  @Override
  public Optional<Follow> findByFollowerId(Long followerId) {
    FollowJpaEntity entity = jpaQueryFactory.selectFrom(followJpaEntity)
        .where(followJpaEntity.followerId.eq(followerId)
            .and(followJpaEntity.deletedAt.isNull()))
        .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(followMapper.toDomain(entity));
  }

  @Override
  public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
    FollowJpaEntity entity = jpaQueryFactory.selectFrom(followJpaEntity)
        .where(followJpaEntity.followerId.eq(followerId)
            .and(followJpaEntity.followingId.eq(followingId))
            .and(followJpaEntity.deletedAt.isNull()))
        .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(followMapper.toDomain(entity));
  }

  @Override
  public Long findFollowingCountByFollowerId(Long followerId) {
    return jpaQueryFactory.select(followJpaEntity.count())
        .from(followJpaEntity)
        .where(followJpaEntity.followerId.eq(followerId)
            .and(followJpaEntity.deletedAt.isNull()))
        .fetchOne();
  }

  @Override
  public Long findFollowerCountByFollowingId(Long followingId) {
    return jpaQueryFactory.select(followJpaEntity.count())
        .from(followJpaEntity)
        .where(followJpaEntity.followingId.eq(followingId)
            .and(followJpaEntity.deletedAt.isNull()))
        .fetchOne();
  }
}
