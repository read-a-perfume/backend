package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static io.perfume.api.note.adapter.out.persistence.note.QNoteJpaEntity.noteJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfume.QPerfumeJpaEntity.perfumeJpaEntity;
import static io.perfume.api.perfume.adapter.out.persistence.perfumeNote.QPerfumeNoteEntity.perfumeNoteEntity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeNote.PerfumeNoteJpaRepository;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

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
}
