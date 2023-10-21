package io.perfume.api.perfume.adapter.out.persistence.perfumeFollow;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeFollow.mapper.PerfumeFollowMapper;
import io.perfume.api.perfume.application.port.out.PerfumeFollowRepository;
import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.perfume.domain.PerfumeFollow;

@PersistenceAdapter
public class PerfumeFollowPersistenceAdapter implements PerfumeFollowRepository {

  private final PerfumeFollowJpaRepository perfumeFollowJpaRepository;
  private final PerfumeFollowMapper perfumeFollowMapper;

  public PerfumeFollowPersistenceAdapter(PerfumeFollowJpaRepository perfumeFollowJpaRepository,
      PerfumeFollowMapper perfumeFollowMapper) {
    this.perfumeFollowJpaRepository = perfumeFollowJpaRepository;
    this.perfumeFollowMapper = perfumeFollowMapper;
  }

  @Override
  public PerfumeFollow save(PerfumeFollow perfumeFollow) {
    PerfumeFollowJpaEntity perfumeFollowJpaEntity = perfumeFollowMapper.toEntity(perfumeFollow);
    perfumeFollowJpaRepository.save(perfumeFollowJpaEntity);
    return perfumeFollowMapper.toDomain(perfumeFollowJpaEntity);
  }
}
