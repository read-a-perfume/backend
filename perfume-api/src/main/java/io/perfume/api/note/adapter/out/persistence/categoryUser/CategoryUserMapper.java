package io.perfume.api.note.adapter.out.persistence.categoryUser;

import io.perfume.api.note.adapter.out.persistence.category.CategoryMapper;
import io.perfume.api.note.domain.CategoryUser;
import org.springframework.stereotype.Component;

@Component
public class CategoryUserMapper {

  private final CategoryMapper categoryMapper;

  public CategoryUserMapper(CategoryMapper categoryMapper) {
    this.categoryMapper = categoryMapper;
  }

  public CategoryUser toDomain(CategoryUserJpaEntity entity) {
    return new CategoryUser(
        entity.getId(),
        entity.getUserId(),
        categoryMapper.toDomain(entity.getCategory()),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt()
    );
  }

  public CategoryUserJpaEntity toEntity(CategoryUser domain) {
    return new CategoryUserJpaEntity(
        domain.getId(),
        categoryMapper.toEntity(domain.getCategory()),
        domain.getUserId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt()
    );
  }
}
