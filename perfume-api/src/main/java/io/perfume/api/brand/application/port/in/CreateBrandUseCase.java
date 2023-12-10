package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.in.dto.CreateBrandCommand;

public interface CreateBrandUseCase {
  BrandResult create(CreateBrandCommand command);
}
