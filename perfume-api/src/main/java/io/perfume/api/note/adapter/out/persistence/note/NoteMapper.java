package io.perfume.api.note.adapter.out.persistence.note;

import io.perfume.api.note.domain.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

  public Note toDomain(NoteJpaEntity entity) {
    return new Note(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        entity.getThumbnailId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public NoteJpaEntity toEntity(Note domain) {
    return new NoteJpaEntity(
        domain.getId(),
        domain.getName(),
        domain.getDescription(),
        domain.getThumbnailId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
