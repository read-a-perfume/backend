package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.mapper.PerfumeFavoriteMapper;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteRepository;
import io.perfume.api.perfume.domain.PerfumeFavorite;

@PersistenceAdapter
public class PerfumeFavoritePersistenceAdapter implements PerfumeFavoriteRepository {

  private final PerfumeFavoriteJpaRepository perfumeFollowJpaRepository;
  private final PerfumeFavoriteMapper perfumeFollowMapper;

  public PerfumeFavoritePersistenceAdapter(
      PerfumeFavoriteJpaRepository perfumeFollowJpaRepository,
      PerfumeFavoriteMapper perfumeFollowMapper) {
    this.perfumeFollowJpaRepository = perfumeFollowJpaRepository;
    this.perfumeFollowMapper = perfumeFollowMapper;
  }

  @Override
  public PerfumeFavorite save(PerfumeFavorite perfumeFavorite) {
    PerfumeFavoriteJpaEntity perfumeFollowJpaEntity = perfumeFollowMapper.toEntity(perfumeFavorite);
    perfumeFollowJpaRepository.save(perfumeFollowJpaEntity);
    return perfumeFollowMapper.toDomain(perfumeFollowJpaEntity);
  }
}
