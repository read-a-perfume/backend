package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import java.io.FileNotFoundException;

public interface FindBrandUseCase {
  BrandResult findBrandById(Long id) throws FileNotFoundException;

  BrandForPerfumeResult findBrandForPerfume(Long id);
}
