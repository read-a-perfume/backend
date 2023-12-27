package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeThemeResult;

public record SimplePerfumeThemeResponseDto(
    Long id, String name, String story, String thumbnail, String brandName) {
  public static SimplePerfumeThemeResponseDto of(
      SimplePerfumeThemeResult simplePerfumeThemeResult) {
    return new SimplePerfumeThemeResponseDto(
        simplePerfumeThemeResult.id(),
        simplePerfumeThemeResult.name(),
        simplePerfumeThemeResult.story(),
        simplePerfumeThemeResult.thumbnail(),
        simplePerfumeThemeResult.brandName());
  }
}
