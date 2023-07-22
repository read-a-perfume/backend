package io.perfume.api.note.adapter.out.persistence;

import io.perfume.api.note.domain.NoteUser;
import org.springframework.stereotype.Component;

@Component
public class NoteUserMapper {

  public NoteUser toDomain(NoteUserJpaEntity entity) {
    return new NoteUser(
        entity.getId(),
        entity.getUserId(),
        entity.getNoteId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public NoteUserJpaEntity toEntity(NoteUser domain) {
    return new NoteUserJpaEntity(
        domain.getId(),
        domain.getUserId(),
        domain.getNoteId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
