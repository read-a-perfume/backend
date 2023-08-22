package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.Brand;

import java.util.Optional;

public interface BrandRepository {
    Optional<Brand> save(Brand brand);
}
