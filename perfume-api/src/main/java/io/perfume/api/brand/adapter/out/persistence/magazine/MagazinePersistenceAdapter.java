package io.perfume.api.brand.adapter.out.persistence.magazine;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.MagazineRepository;
import io.perfume.api.brand.domain.Magazine;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MagazinePersistenceAdapter implements MagazineRepository {

  private final MagazineMapper magazineMapper;

  private final MagazineJpaRepository magazineJpaRepository;

  @Override
  public Magazine save(Magazine magazine) {
    MagazineEntity magazineEntity = magazineJpaRepository.save(magazineMapper.toEntity(magazine));
    return magazineMapper.toDomain(magazineEntity);
  }
}
