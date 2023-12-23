package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.BrandResult;
import lombok.Builder;

@Builder
public record BrandResponse(Long id, String name, String story, String brandUrl, String thumbnail) {

  public static BrandResponse of(BrandResult brandResult) {
    return BrandResponse.builder()
        .id(brandResult.id())
        .name(brandResult.name())
        .story(brandResult.story())
        .brandUrl(brandResult.brandUrl())
        .thumbnail(brandResult.thumbnail())
        .build();
  }
}
