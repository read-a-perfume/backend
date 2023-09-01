package io.perfume.api.brand.application.port.in.dto;

import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.domain.File;

public record BrandResult(String name, String story, String thumbnailUrl) {
  public static BrandResult of(Brand brand, File file) {
    return new BrandResult(brand.getName(), brand.getStory(), file.getUrl());
  }
}
