package io.perfume.api.perfume.adapter.out.persistence.perfumeFollow;

import static io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.QPerfumeFollowJpaEntity.perfumeFollowJpaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.mapper.PerfumeFollowMapper;
import io.perfume.api.perfume.application.port.out.PerfumeFollowQueryRepository;
import io.perfume.api.perfume.domain.PerfumeFollow;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
public class PerfumeFollowQueryPersistenceAdapter implements PerfumeFollowQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final PerfumeFollowMapper perfumeFollowMapper;

  public PerfumeFollowQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory,
      PerfumeFollowMapper perfumeFollowMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.perfumeFollowMapper = perfumeFollowMapper;
  }

  @Override
  public Optional<PerfumeFollow> findByUserAndPerfume(Long userId, Long perfumeId) {
    PerfumeFollowJpaEntity entity = jpaQueryFactory.select(perfumeFollowJpaEntity)
        .from(perfumeFollowJpaEntity)
        .where(perfumeFollowJpaEntity.userId.eq(userId)
            .and(perfumeFollowJpaEntity.perfumeId.eq(perfumeId)))
        .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(perfumeFollowMapper.toDomain(entity));
  }

  @Override
  public List<PerfumeFollow> findFollowedPerfumesByUser(Long userId) {
    return jpaQueryFactory
        .selectFrom(perfumeFollowJpaEntity)
        .where(perfumeFollowJpaEntity.userId.eq(userId))
        .fetch()
        .stream()
        .map(perfumeFollowMapper::toDomain)
        .collect(Collectors.toList());
  }
}
