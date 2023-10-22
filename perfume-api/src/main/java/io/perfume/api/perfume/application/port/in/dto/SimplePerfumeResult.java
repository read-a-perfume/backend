package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.Perfume;
import lombok.Builder;

@Builder
public record SimplePerfumeResult(Long id, String name, Concentration concentration, String brandName, String thumbnailUrl) {
  public static SimplePerfumeResult from(Perfume perfume, BrandForPerfumeResult brandResult) {
    return SimplePerfumeResult.builder()
        .id(perfume.getId())
        .name(perfume.getName())
        .concentration(perfume.getConcentration())
        .brandName(brandResult.name())
        .thumbnailUrl(null) // TODO: 썸네일 추가
        .build();
  }
}
