package io.perfume.api.brand.adapter.out.persistence.tag;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.MagazineTagRepository;
import io.perfume.api.brand.domain.MagazineTag;
import java.util.List;
import java.util.stream.StreamSupport;

@PersistenceAdapter
public class MagazineTagPersistenceAdapter implements MagazineTagRepository {

  private final MagazineTagJpaRepository magazineTagJpaRepository;

  private final MagazineTagMapper magazineTagMapper;

  public MagazineTagPersistenceAdapter(
      MagazineTagJpaRepository magazineTagJpaRepository, MagazineTagMapper magazineTagMapper) {
    this.magazineTagJpaRepository = magazineTagJpaRepository;
    this.magazineTagMapper = magazineTagMapper;
  }

  @Override
  public MagazineTag save(MagazineTag magazineTag) {
    MagazineTagEntity magazineTagEntity =
        magazineTagJpaRepository.save(magazineTagMapper.toEntity(magazineTag));
    return magazineTagMapper.toDomain(magazineTagEntity);
  }

  @Override
  public List<MagazineTag> saveAll(List<MagazineTag> magazineTags) {
    var entities = magazineTags.stream().map(magazineTagMapper::toEntity).toList();
    return StreamSupport.stream(magazineTagJpaRepository.saveAll(entities).spliterator(), true)
        .map(magazineTagMapper::toDomain)
        .toList();
  }
}
