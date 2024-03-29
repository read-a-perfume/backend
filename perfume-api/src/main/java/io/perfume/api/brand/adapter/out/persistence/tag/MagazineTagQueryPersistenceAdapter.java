package io.perfume.api.brand.adapter.out.persistence.tag;

import static io.perfume.api.brand.adapter.out.persistence.tag.QMagazineTagEntity.magazineTagEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.MagazineTagQueryRepository;
import io.perfume.api.brand.domain.MagazineTag;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MagazineTagQueryPersistenceAdapter implements MagazineTagQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final MagazineTagMapper magazineTagMapper;

  @Override
  public List<MagazineTag> findMagazinesTags(Long magazineIds) {
    return jpaQueryFactory
        .selectFrom(magazineTagEntity)
        .where(magazineTagEntity.id.magazineId.eq(magazineIds))
        .fetch()
        .stream()
        .map(magazineTagMapper::toDomain)
        .toList();
  }
}
