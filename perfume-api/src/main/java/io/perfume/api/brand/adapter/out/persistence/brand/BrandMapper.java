package io.perfume.api.brand.adapter.out.persistence.brand;

import io.perfume.api.brand.domain.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
  public Brand toDomain(BrandEntity brandEntity) {
    if (brandEntity == null) {
      return null;
    }
    return Brand.builder()
        .id(brandEntity.getId())
        .name(brandEntity.getName())
        .story(brandEntity.getStory())
        .thumbnailId(brandEntity.getThumbnailId())
        .createdAt(brandEntity.getCreatedAt())
        .updatedAt(brandEntity.getUpdatedAt())
        .deletedAt(brandEntity.getDeletedAt())
        .build();
  }

  public BrandEntity toEntity(Brand brand) {
    if (brand == null) {
      return null;
    }
    return BrandEntity.builder()
        .id(brand.getId())
        .name(brand.getName())
        .story(brand.getStory())
        .thumbnailId(brand.getThumbnailId())
        .build();
  }
}
