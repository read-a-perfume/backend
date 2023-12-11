package io.perfume.api.brand.adapter.out.persistence.magazine;

import static io.perfume.api.brand.adapter.out.persistence.magazine.QMagazineEntity.magazineEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.domain.Magazine;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MagazineQueryPersistenceAdapter implements MagazineQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final MagazineMapper magazineMapper;

  @Override
  public Optional<Magazine> findById(Long id) {
    MagazineEntity entity =
        jpaQueryFactory
            .selectFrom(magazineEntity)
            .where(magazineEntity.id.eq(id).and(magazineEntity.deletedAt.isNull()))
            .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(magazineMapper.toDomain(entity));
  }
}
