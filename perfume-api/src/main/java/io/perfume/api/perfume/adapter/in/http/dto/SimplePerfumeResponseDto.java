package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;

public record SimplePerfumeResponseDto(
    Long id, String name, String thumbnail, String brandName, String strength, String duration) {
  public static SimplePerfumeResponseDto of(SimplePerfumeResult simplePerfumeResult) {
    return new SimplePerfumeResponseDto(
        simplePerfumeResult.id(),
        simplePerfumeResult.name(),
        simplePerfumeResult.thumbnail(),
        simplePerfumeResult.brandName(),
        simplePerfumeResult.concentration().getStrength(),
        simplePerfumeResult.concentration().getDuration());
  }
}
