package io.perfume.api.perfume.application.port.in.dto;

import io.perfume.api.perfume.domain.Perfume;

public record PerfumeFavoriteResult(String perfumeName) {

  public static PerfumeFavoriteResult from(Perfume perfume) {
    return new PerfumeFavoriteResult(perfume.getName());
  }
}
