package io.perfume.api.note.adapter.out.persistence.noteUser;

import io.perfume.api.note.adapter.out.persistence.note.NoteMapper;
import io.perfume.api.note.domain.NoteUser;
import org.springframework.stereotype.Component;

@Component
public class CategoryUserMapper {

  private final NoteMapper noteMapper;

  public CategoryUserMapper(NoteMapper noteMapper) {
    this.noteMapper = noteMapper;
  }

  public NoteUser toDomain(CategoryUserJpaEntity entity) {
    return new NoteUser(
        entity.getId(),
        entity.getUserId(),
        noteMapper.toDomain(entity.getNote()),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public CategoryUserJpaEntity toEntity(NoteUser domain) {
    return new CategoryUserJpaEntity(
        domain.getId(),
        noteMapper.toEntity(domain.getNote()),
        domain.getUserId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
