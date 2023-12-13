package io.perfume.api.brand.adapter.out.persistence.magazine;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dto.repository.CursorDirection;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.domain.Magazine;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static io.perfume.api.brand.adapter.out.persistence.magazine.QMagazineEntity.magazineEntity;

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

  @Override
  public List<Magazine> findByMagazines(Long brandId) {
    return jpaQueryFactory
            .selectFrom(magazineEntity)
            .where(
                    magazineEntity.brandId.eq(brandId),
                    magazineEntity.deletedAt.isNull())
            .orderBy(magazineEntity.id.desc())
            .fetch()
            .stream()
            .map(magazineMapper::toDomain)
            .toList();
  }

  @Override
  public CursorPagination<Magazine> findByBrandId(CursorPageable<Long> pageable, Long brandId) {
    var qb = jpaQueryFactory
            .selectFrom(magazineEntity)
            .where(
                    magazineEntity.brandId.eq(brandId)
                            .and(magazineEntity.deletedAt.isNull()));

    if (pageable.getCursor() != null) {
      if (pageable.getDirection() == CursorDirection.NEXT) {
        qb.where(magazineEntity.id.gt(pageable.getCursor()));
      } else {
        qb.where(magazineEntity.id.lt(pageable.getCursor()));
      }
    }

    var magazines = qb.limit(pageable.getSize()).fetch().stream().map(magazineMapper::toDomain).toList();
    return CursorPagination.of(
            magazines, pageable.getSize(), pageable.getDirection(), pageable.getCursor() != null
    );
  }
}
