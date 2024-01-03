package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static io.perfume.api.brand.adapter.out.persistence.brand.QBrandEntity.brandEntity;
import static io.perfume.api.file.adapter.out.persistence.file.QFileJpaEntity.fileJpaEntity;
import static io.perfume.api.note.adapter.out.persistence.note.QNoteJpaEntity.noteJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfume.QPerfumeJpaEntity.perfumeJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite.QPerfumeFavoriteJpaEntity.perfumeFavoriteJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfumeImage.QPerfumeImageEntity.perfumeImageEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfumeNote.QPerfumeNoteEntity.perfumeNoteEntity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeThemeResult;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumeQueryPersistenceAdapter implements PerfumeQueryRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final PerfumeMapper perfumeMapper;

  @Override
  public boolean existsPerfumeById(Long id) {
    return jpaQueryFactory
            .from(perfumeJpaEntity)
            .where(perfumeJpaEntity.id.eq(id), perfumeJpaEntity.deletedAt.isNull())
            .fetchFirst()
        != null;
  }

  @Override
  public Optional<Perfume> findPerfumeById(Long id) {
    PerfumeJpaEntity entity =
        jpaQueryFactory
            .select(perfumeJpaEntity)
            .from(perfumeJpaEntity)
            .where(perfumeJpaEntity.id.eq(id).and(perfumeJpaEntity.deletedAt.isNull()))
            .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    List<Long> perfumeImageIds = findPerfumeImagesById(id);
    return Optional.of(perfumeMapper.toPerfumeWithImages(entity, perfumeImageIds));
  }

  private List<Long> findPerfumeImagesById(Long id) {
    return jpaQueryFactory
        .select(perfumeImageEntity.perfumeImageId.imageId)
        .from(perfumeImageEntity)
        .where(perfumeImageEntity.perfumeImageId.perfumeId.eq(id))
        .fetch();
  }

  @Override
  public List<SimplePerfumeThemeResult> findPerfumesByIds(List<Long> ids) {
    return jpaQueryFactory
        .select(
            Projections.constructor(
                SimplePerfumeThemeResult.class,
                perfumeJpaEntity.id,
                perfumeJpaEntity.name,
                perfumeJpaEntity.story,
                brandEntity.name,
                fileJpaEntity.url))
        .from(perfumeJpaEntity)
        .where(perfumeJpaEntity.id.in(ids), perfumeJpaEntity.deletedAt.isNull())
        .leftJoin(brandEntity)
        .on(perfumeJpaEntity.brandId.eq(brandEntity.id))
        .fetchJoin()
        .leftJoin(fileJpaEntity)
        .on(perfumeJpaEntity.thumbnailId.eq(fileJpaEntity.id))
        .fetchJoin()
        .fetch();
  }

  @Override
  public NotePyramid getNotePyramidByPerfume(Long perfumeId) {
    List<Tuple> result =
        jpaQueryFactory
            .select(
                perfumeNoteEntity.noteId,
                perfumeNoteEntity.noteLevel,
                noteJpaEntity.name,
                noteJpaEntity.thumbnailId)
            .from(perfumeNoteEntity)
            .where(perfumeNoteEntity.deletedAt.isNull(), perfumeNoteEntity.perfumeId.eq(perfumeId))
            .leftJoin(noteJpaEntity)
            .on(perfumeNoteEntity.noteId.eq(noteJpaEntity.id))
            .fetchJoin()
            .fetch();

    return perfumeMapper.toNotePyramid(result);
  }

  @Override
  public CustomSlice<SimplePerfumeResult> findPerfumesByBrand(
      Long brandId, Long lastPerfumeId, int pageSize) {
    List<SimplePerfumeResult> results =
        jpaQueryFactory
            .select(
                Projections.constructor(
                    SimplePerfumeResult.class,
                    perfumeJpaEntity.id,
                    perfumeJpaEntity.name,
                    perfumeJpaEntity.concentration,
                    brandEntity.name,
                    fileJpaEntity.url))
            .from(perfumeJpaEntity)
            .where(
                ltPerfumeId(lastPerfumeId),
                perfumeJpaEntity.brandId.eq(brandId),
                perfumeJpaEntity.deletedAt.isNull())
            .leftJoin(brandEntity)
            .on(perfumeJpaEntity.brandId.eq(brandEntity.id))
            .fetchJoin()
            .leftJoin(fileJpaEntity)
            .on(perfumeJpaEntity.thumbnailId.eq(fileJpaEntity.id))
            .fetchJoin()
            .orderBy(perfumeJpaEntity.id.desc())
            .limit(pageSize + 1L)
            .fetch();

    boolean hasNext = false;
    if (results.size() > pageSize) {
      results.remove(pageSize);
      hasNext = true;
    }

    return new CustomSlice<>(results, hasNext);
  }

  @Override
  public CustomPage<SimplePerfumeResult> findPerfumesByCategory(
      Long categoryId, Pageable pageable) {

    List<SimplePerfumeResult> results =
        jpaQueryFactory
            .select(
                Projections.constructor(
                    SimplePerfumeResult.class,
                    perfumeJpaEntity.id,
                    perfumeJpaEntity.name,
                    perfumeJpaEntity.concentration,
                    brandEntity.name,
                    fileJpaEntity.url))
            .from(perfumeJpaEntity)
            .where(perfumeJpaEntity.categoryId.eq(categoryId), perfumeJpaEntity.deletedAt.isNull())
            .leftJoin(brandEntity)
            .on(perfumeJpaEntity.brandId.eq(brandEntity.id))
            .fetchJoin()
            .leftJoin(fileJpaEntity)
            .on(perfumeJpaEntity.thumbnailId.eq(fileJpaEntity.id))
            .fetchJoin()
            .orderBy(perfumeJpaEntity.id.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

    Long total =
        jpaQueryFactory
            .select(perfumeJpaEntity.count())
            .from(perfumeJpaEntity)
            .where(perfumeJpaEntity.categoryId.eq(categoryId), perfumeJpaEntity.deletedAt.isNull())
            .fetchOne();

    return new CustomPage<>(new PageImpl<>(results, pageable, total == null ? 0 : total));
  }

  @Override
  public CustomSlice<SimplePerfumeResult> findPerfumesByFavorite(Long lastPerfumeId, int pageSize) {
    Long lastFavoriteCount = Long.MAX_VALUE;
    if (lastPerfumeId != null) {
      lastFavoriteCount =
          jpaQueryFactory
              .select(perfumeFavoriteJpaEntity.id.perfumeId.count())
              .from(perfumeJpaEntity)
              .where(perfumeJpaEntity.deletedAt.isNull(), perfumeJpaEntity.id.eq(lastPerfumeId))
              .leftJoin(perfumeFavoriteJpaEntity)
              .on(perfumeJpaEntity.id.eq(perfumeFavoriteJpaEntity.id.perfumeId))
              .fetchJoin()
              .fetchOne();
    }

    List<SimplePerfumeResult> results =
        jpaQueryFactory
            .select(
                Projections.constructor(
                    SimplePerfumeResult.class,
                    perfumeJpaEntity.id,
                    perfumeJpaEntity.name,
                    perfumeJpaEntity.concentration,
                    brandEntity.name,
                    fileJpaEntity.url))
            .from(perfumeJpaEntity)
            .leftJoin(perfumeFavoriteJpaEntity)
            .on(perfumeJpaEntity.id.eq(perfumeFavoriteJpaEntity.id.perfumeId))
            .fetchJoin()
            .leftJoin(brandEntity)
            .on(perfumeJpaEntity.brandId.eq(brandEntity.id))
            .fetchJoin()
            .leftJoin(fileJpaEntity)
            .on(perfumeJpaEntity.thumbnailId.eq(fileJpaEntity.id))
            .fetchJoin()
            .where(perfumeJpaEntity.deletedAt.isNull(), ltPerfumeId(lastPerfumeId))
            .groupBy(perfumeJpaEntity.id)
            .having(perfumeFavoriteJpaEntity.id.perfumeId.count().loe(lastFavoriteCount))
            .orderBy(
                perfumeFavoriteJpaEntity.id.perfumeId.count().desc().nullsLast(),
                perfumeJpaEntity.id.desc())
            .limit(pageSize + 1L)
            .fetch();

    boolean hasNext = false;
    if (results.size() > pageSize) {
      results.remove(pageSize);
      hasNext = true;
    }

    return new CustomSlice<>(results, hasNext);
  }

  @Override
  public List<PerfumeNameResult> searchPerfumeByQuery(String query) {

    return jpaQueryFactory
        .select(
            Projections.constructor(
                PerfumeNameResult.class,
                brandEntity.name,
                perfumeJpaEntity.name,
                perfumeJpaEntity.id))
        .from(perfumeJpaEntity)
        .leftJoin(brandEntity)
        .on(perfumeJpaEntity.brandId.eq(brandEntity.id))
        .fetchJoin()
        .where(
            perfumeJpaEntity.deletedAt.isNull(),
            brandEntity.name.append(" ").append(perfumeJpaEntity.name).contains(query))
        .limit(10L)
        .fetch();
  }

  private BooleanExpression ltPerfumeId(Long perfumeId) {
    if (perfumeId == null) {
      return null;
    }

    return perfumeJpaEntity.id.lt(perfumeId);
  }
}
