package io.perfume.api.brand.application.port.in.dto;

import io.perfume.api.brand.domain.Brand;

public record BrandForPerfumeResult(String name) {
  public static BrandForPerfumeResult of(Brand brand) {
    return new BrandForPerfumeResult(brand.getName());
  }
}
