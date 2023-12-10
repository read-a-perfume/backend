package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;

public record PerfumeNameResponseDto(String perfumeNameWithBrand, Long perfumeId) {
  public static PerfumeNameResponseDto of(PerfumeNameResult perfumeNameResult) {
    return new PerfumeNameResponseDto(
        perfumeNameResult.perfumeNameWithBrand(), perfumeNameResult.perfumeId());
  }
}
