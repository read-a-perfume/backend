package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.perfume.application.port.in.dto.PerfumeFavoriteResult;
import java.util.List;

public record PerfumeFavoriteResponseDto(String perfumeName) {
  public static List<PerfumeFavoriteResponseDto> from(List<PerfumeFavoriteResult> result) {
    return result.stream()
        .map(
            perfumeFavoriteResult ->
                new PerfumeFavoriteResponseDto(perfumeFavoriteResult.perfumeName()))
        .toList();
  }
}
