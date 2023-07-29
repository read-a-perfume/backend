package io.perfume.api.note.adapter.out.persistence.noteUser;

import io.perfume.api.note.adapter.out.persistence.note.NoteMapper;
import io.perfume.api.note.domain.NoteUser;
import org.springframework.stereotype.Component;

@Component
public class NoteUserMapper {

  private final NoteMapper noteMapper;

  public NoteUserMapper(NoteMapper noteMapper) {
    this.noteMapper = noteMapper;
  }

  public NoteUser toDomain(NoteUserJpaEntity entity) {
    return new NoteUser(
        entity.getId(),
        entity.getUserId(),
        noteMapper.toDomain(entity.getNote()),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public NoteUserJpaEntity toEntity(NoteUser domain) {
    return new NoteUserJpaEntity(
        domain.getId(),
        noteMapper.toEntity(domain.getNote()),
        domain.getUserId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
