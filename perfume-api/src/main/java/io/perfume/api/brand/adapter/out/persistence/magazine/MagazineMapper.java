package io.perfume.api.brand.adapter.out.persistence.magazine;

import io.perfume.api.brand.domain.Magazine;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MagazineMapper {
  public Magazine toDomain(@NotNull MagazineEntity magazineEntity) {
    if (magazineEntity == null) {
      return null;
    }
    return new Magazine(
        magazineEntity.getId(),
        magazineEntity.getTitle(),
        magazineEntity.getSubTitle(),
        magazineEntity.getContent(),
        magazineEntity.getCoverThumbnailId(),
        magazineEntity.getThumbnailId(),
        magazineEntity.getUserId(),
        magazineEntity.getBrandId(),
        magazineEntity.getCreatedAt(),
        magazineEntity.getUpdatedAt(),
        magazineEntity.getDeletedAt());
  }

  public MagazineEntity toEntity(@NotNull Magazine magazine) {
    return new MagazineEntity(
        magazine.getId(),
        magazine.getTitle(),
        magazine.getSubTitle(),
        magazine.getContent(),
        magazine.getCoverThumbnailId(),
        magazine.getThumbnailId(),
        magazine.getUserId(),
        magazine.getBrandId(),
        magazine.getCreatedAt(),
        magazine.getUpdatedAt(),
        magazine.getDeletedAt());
  }
}
