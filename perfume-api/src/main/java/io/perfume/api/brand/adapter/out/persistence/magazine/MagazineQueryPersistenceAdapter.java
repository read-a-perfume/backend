package io.perfume.api.brand.adapter.out.persistence.magazine;

import static io.perfume.api.brand.adapter.out.persistence.magazine.QMagazineEntity.magazineEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.MagazineQueryRepository;
import io.perfume.api.brand.domain.Magazine;
import java.time.LocalDateTime;
import java.util.List;
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

  @Override
  public List<Magazine> findByMagazines(Long brandId) {
    return jpaQueryFactory
        .selectFrom(magazineEntity)
        .where(magazineEntity.brandId.eq(brandId), magazineEntity.deletedAt.isNull())
        .orderBy(magazineEntity.id.desc())
        .fetch()
        .stream()
        .map(magazineMapper::toDomain)
        .toList();
  }

  @Override
  public CursorPagination<Magazine> findByBrandId(CursorPageable pageable, Long brandId) {
    var qb =
        jpaQueryFactory
            .selectFrom(magazineEntity)
            .where(magazineEntity.brandId.eq(brandId).and(magazineEntity.deletedAt.isNull()))
            .orderBy(magazineEntity.createdAt.desc());

    final Optional<LocalDateTime> cursor = pageable.getCursor(LocalDateTime::parse);
    if (cursor.isPresent()) {
      final LocalDateTime id = cursor.get();
      if (pageable.isNext()) {
        qb.where(magazineEntity.createdAt.lt(id));
      } else {
        qb.where(magazineEntity.createdAt.gt(id));
      }
    }

    final List<Magazine> magazines =
        qb.limit(pageable.getFetchSize()).fetch().stream().map(magazineMapper::toDomain).toList();
    return CursorPagination.of(
        magazines, pageable, (magazine -> magazine.getCreatedAt().toString()));
  }
}
