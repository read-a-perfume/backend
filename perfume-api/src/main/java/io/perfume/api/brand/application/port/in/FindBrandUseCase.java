package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.brand.application.port.in.dto.BrandResult;

public interface FindBrandUseCase {
  BrandResult findBrandById(Long id);

  BrandForPerfumeResult findBrandForPerfume(Long id);
}
