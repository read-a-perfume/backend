package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static io.perfume.api.brand.adapter.out.persistence.QBrandEntity.brandEntity;
import static io.perfume.api.file.adapter.out.persistence.file.QFileJpaEntity.fileJpaEntity;
import static io.perfume.api.note.adapter.out.persistence.note.QNoteJpaEntity.noteJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfume.QPerfumeJpaEntity.perfumeJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfumeNote.QPerfumeNoteEntity.perfumeNoteEntity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteJpaRepository;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumeQueryPersistenceAdapter implements PerfumeQueryRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final PerfumeNoteJpaRepository perfumeNoteJpaRepository;
  private final PerfumeMapper perfumeMapper;

  @Override
  public Optional<Perfume> findPerfumeById(Long id) {
    PerfumeJpaEntity entity = jpaQueryFactory.select(perfumeJpaEntity)
        .from(perfumeJpaEntity)
        .where(perfumeJpaEntity.id.eq(id)
            .and(perfumeJpaEntity.deletedAt.isNull()))
        .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(perfumeMapper.toPerfume(entity));
  }

  @Override
  public NotePyramid getNotePyramidByPerfume(Long perfumeId) {
    List<Tuple> result = jpaQueryFactory
        .select(perfumeNoteEntity.noteId, perfumeNoteEntity.noteLevel, noteJpaEntity.name, noteJpaEntity.thumbnailId)
        .from(perfumeNoteEntity)
        .where(perfumeNoteEntity.deletedAt.isNull())
        .leftJoin(noteJpaEntity).on(perfumeNoteEntity.noteId.eq(noteJpaEntity.id)).fetchJoin()
        .fetch();

    return perfumeMapper.toNotePyramid(result);
  }

  @Override
  public Slice<SimplePerfumeResult> findPerfumesByBrand(Long brandId, Long lastPerfumeId, int pageSize) {
    List<SimplePerfumeResult> results = jpaQueryFactory.select(
            Projections.constructor(SimplePerfumeResult.class, perfumeJpaEntity.id, perfumeJpaEntity.name, perfumeJpaEntity.concentration,
                brandEntity.name, fileJpaEntity.url))
        .from(perfumeJpaEntity)
        .where(ltPerfumeId(lastPerfumeId),
            perfumeJpaEntity.brandId.eq(brandId),
            perfumeJpaEntity.deletedAt.isNull())
        .leftJoin(brandEntity).on(perfumeJpaEntity.brandId.eq(brandEntity.id)).fetchJoin()
        .leftJoin(fileJpaEntity).on(perfumeJpaEntity.thumbnailId.eq(fileJpaEntity.id)).fetchJoin()
        .orderBy(perfumeJpaEntity.id.desc())
        .limit(pageSize + 1L)
        .fetch();

    boolean hasNext = false;
    if (results.size() > pageSize) {
      results.remove(pageSize);
      hasNext = true;
    }

    return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
  }

  private BooleanExpression ltPerfumeId(Long perfumeId) {
    if (perfumeId == null) {
      return null;
    }

    return perfumeJpaEntity.id.lt(perfumeId);
  }

  @Override
  public Page<SimplePerfumeResult> findPerfumesByCategory(Long categoryId, Pageable pageable) {

    List<SimplePerfumeResult> results = jpaQueryFactory.select(
            Projections.constructor(SimplePerfumeResult.class, perfumeJpaEntity.id, perfumeJpaEntity.name, perfumeJpaEntity.concentration,
                brandEntity.name, fileJpaEntity.url))
        .from(perfumeJpaEntity)
        .where(
            perfumeJpaEntity.categoryId.eq(categoryId),
            perfumeJpaEntity.deletedAt.isNull())
        .leftJoin(brandEntity).on(perfumeJpaEntity.brandId.eq(brandEntity.id)).fetchJoin()
        .leftJoin(fileJpaEntity).on(perfumeJpaEntity.thumbnailId.eq(fileJpaEntity.id)).fetchJoin()
        .orderBy(perfumeJpaEntity.id.desc())
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .fetch();

    Long total = jpaQueryFactory.select(perfumeJpaEntity.count())
        .from(perfumeJpaEntity)
        .where(
            perfumeJpaEntity.categoryId.eq(categoryId),
            perfumeJpaEntity.deletedAt.isNull()
        )
        .fetchOne();

    return new PageImpl<>(results, pageable, total == null ? 0 : total);
  }
}
