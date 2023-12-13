package io.perfume.api.brand.adapter.out.persistence.tag;

import io.perfume.api.brand.domain.TagName;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class TagNameMapper {

  public TagName toDomain(@NotNull TagNameEntity tagNameEntity) {
    if (tagNameEntity == null) {
      return null;
    }
    return new TagName(
        tagNameEntity.getId(),
        tagNameEntity.getName(),
        tagNameEntity.getCreatedAt(),
        tagNameEntity.getUpdatedAt(),
        tagNameEntity.getDeletedAt());
  }

  public TagNameEntity toEntity(@NotNull TagName tagName) {
    return new TagNameEntity(
        tagName.getId(),
        tagName.getName(),
        tagName.getCreatedAt(),
        tagName.getUpdatedAt(),
        tagName.getDeletedAt());
  }
}
