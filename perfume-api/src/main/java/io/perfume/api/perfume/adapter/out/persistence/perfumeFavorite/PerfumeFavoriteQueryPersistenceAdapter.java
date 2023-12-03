package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import static io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.QPerfumeFavoriteJpaEntity.perfumeFavoriteJpaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.mapper.PerfumeFavoriteMapper;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteQueryRepository;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
public class PerfumeFavoriteQueryPersistenceAdapter implements PerfumeFavoriteQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final PerfumeFavoriteMapper perfumeFavoriteMapper;

  public PerfumeFavoriteQueryPersistenceAdapter(
      JPAQueryFactory jpaQueryFactory, PerfumeFavoriteMapper perfumeFavoriteMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.perfumeFavoriteMapper = perfumeFavoriteMapper;
  }

  @Override
  public Optional<PerfumeFavorite> findByUserIdAndPerfumeId(Long userId, Long perfumeId) {
    PerfumeFavoriteJpaEntity entity =
        jpaQueryFactory
            .selectFrom(perfumeFavoriteJpaEntity)
            .where(
                perfumeFavoriteJpaEntity
                    .id
                    .userId
                    .eq(userId)
                    .and(perfumeFavoriteJpaEntity.id.perfumeId.eq(perfumeId)))
            .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(perfumeFavoriteMapper.toDomain(entity));
  }

  @Override
  public List<PerfumeFavorite> findFavoritePerfumesByUserId(Long userId) {
    return jpaQueryFactory
        .selectFrom(perfumeFavoriteJpaEntity)
        .where(
            perfumeFavoriteJpaEntity
                .id
                .userId
                .eq(userId)
                .and(perfumeFavoriteJpaEntity.deletedAt.isNull()))
        .fetch()
        .stream()
        .map(perfumeFavoriteMapper::toDomain)
        .collect(Collectors.toList());
  }
}
