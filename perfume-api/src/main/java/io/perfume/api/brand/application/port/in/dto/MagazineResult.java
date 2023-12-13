package io.perfume.api.brand.application.port.in.dto;

import io.perfume.api.brand.domain.Magazine;

public record MagazineResult(
    Long id,
    String title,
    String subTitle,
    String content,
    Long coverThumbnailId,
    Long thumbnailId,
    Long brandId
) {

  public static MagazineResult from(Magazine magazine) {
    return new MagazineResult(
        magazine.getId(),
        magazine.getTitle(),
        magazine.getSubTitle(),
        magazine.getContent(),
        magazine.getCoverThumbnailId(),
        magazine.getThumbnailId(),
        magazine.getBrandId());
  }
}
