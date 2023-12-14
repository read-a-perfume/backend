package io.perfume.api.brand.application.port.in.dto;

import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.domain.File;

public record BrandResult(Long id, String name, String story, String thumbnail) {
  public static BrandResult of(Brand brand, File file) {
    return new BrandResult(brand.getId(), brand.getName(), brand.getStory(), file.getUrl());
  }
}
