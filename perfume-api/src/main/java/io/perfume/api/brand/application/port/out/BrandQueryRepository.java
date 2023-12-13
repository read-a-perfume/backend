package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandQueryRepository {
  Optional<Brand> findBrandById(Long id);

  List<Brand> findAll();
}
