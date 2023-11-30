package io.perfume.api.perfume.application.port.in.dto;

public record PerfumeNameResult(String perfumeNameWithBrand, Long perfumeId) {
  public PerfumeNameResult(String brandName, String perfumeName, Long perfumeId) {
    this(brandName + " " + perfumeName, perfumeId);
  }
}
