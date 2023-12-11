package io.perfume.api.brand.adapter.out.persistence.tag;

import io.perfume.api.brand.domain.MagazineTag;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MagazineTagMapper {

  public MagazineTag toDomain(@NotNull MagazineTagEntity magazineTagEntity) {
    if (magazineTagEntity == null) {
      return null;
    }
    return new MagazineTag(
        magazineTagEntity.getId().getMagazineId(),
        magazineTagEntity.getId().getTagId(),
        magazineTagEntity.getCreatedAt(),
        magazineTagEntity.getUpdatedAt(),
        magazineTagEntity.getDeletedAt());
  }

  public MagazineTagEntity toEntity(@NotNull MagazineTag magazineTag) {
    return new MagazineTagEntity(
        new MagazineTagId(magazineTag.getMagazineId(), magazineTag.getTagId()),
        magazineTag.getCreatedAt(),
        magazineTag.getUpdatedAt(),
        magazineTag.getDeletedAt());
  }
}
