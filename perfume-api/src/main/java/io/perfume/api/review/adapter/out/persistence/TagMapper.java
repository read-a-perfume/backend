package io.perfume.api.review.adapter.out.persistence;

import io.perfume.api.review.domain.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

  public Tag toDomain(TagEntity entity) {
    return new Tag(
        entity.getId(),
        entity.getName(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public TagEntity toEntity(Tag domain) {
    return new TagEntity(
        domain.getId(),
        domain.getName(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
