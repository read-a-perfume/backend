package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeThemeResult;
import java.util.List;

public record PerfumeThemeResponseDto(
    String title, String content, String thumbnail, List<SimplePerfumeResponseDto> perfumes) {
  public static PerfumeThemeResponseDto from(PerfumeThemeResult perfumeThemeResult) {
    return new PerfumeThemeResponseDto(
        perfumeThemeResult.title(),
        perfumeThemeResult.content(),
        perfumeThemeResult.thumbnail(),
        perfumeThemeResult.perfumes().stream().map(SimplePerfumeResponseDto::of).toList());
  }
}
