package io.perfume.api.note.adapter.out.persistence.note;

import io.perfume.api.note.domain.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

  public Note toDomain(NoteJpaEntity entity) {
    return new Note(
        entity.getId(),
        entity.getName(),
        entity.getThumbnailId(),
        entity.getCategory(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public NoteJpaEntity toEntity(Note domain) {
    return new NoteJpaEntity(
        domain.getId(),
        domain.getName(),
        domain.getThumbnailId(),
        domain.getCategory(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
