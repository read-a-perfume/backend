package io.perfume.api.note.adapter.out.persistence.note;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.note.application.port.out.NoteQueryRepository;
import io.perfume.api.note.domain.Note;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class NoteQueryPersistenceAdapter implements NoteQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final NoteMapper noteMapper;

  public NoteQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory, NoteMapper noteMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.noteMapper = noteMapper;
  }

  @Override
  public List<Note> find() {
    return jpaQueryFactory.selectFrom(QNoteJpaEntity.noteJpaEntity)
        .where(QNoteJpaEntity.noteJpaEntity.deletedAt.isNull())
        .fetch()
        .stream()
        .map(noteMapper::toDomain)
        .toList();
  }

  public Optional<Note> findById(long id) {
    NoteJpaEntity entity = jpaQueryFactory.selectFrom(QNoteJpaEntity.noteJpaEntity)
        .where(QNoteJpaEntity.noteJpaEntity.id.eq(id))
        .fetchOne();

    if (Objects.isNull(entity)) {
      return Optional.empty();
    }

    return Optional.of(noteMapper.toDomain(entity));
  }
}
