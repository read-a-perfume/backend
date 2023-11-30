package io.perfume.api.note.adapter.out.persistence.category;

import io.perfume.api.note.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category toDomain(CategoryJpaEntity entity) {
    return new Category(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        entity.getTags(),
        entity.getThumbnailId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public CategoryJpaEntity toEntity(Category domain) {
    return new CategoryJpaEntity(
        domain.getId(),
        domain.getName(),
        domain.getDescription(),
        domain.getTags(),
        domain.getThumbnailId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
