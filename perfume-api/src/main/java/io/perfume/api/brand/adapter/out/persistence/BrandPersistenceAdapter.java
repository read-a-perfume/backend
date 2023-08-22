package io.perfume.api.brand.adapter.out.persistence;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.BrandRepository;
import io.perfume.api.brand.domain.Brand;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@PersistenceAdapter
@RequiredArgsConstructor
public class BrandPersistenceAdapter implements BrandRepository {

    private final BrandMapper brandMapper;

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Optional<Brand> save(Brand brand) {
        BrandEntity brandEntity = brandJpaRepository.save(brandMapper.toEntity(brand));
        return Optional.ofNullable(brandMapper.toDomain(brandEntity));
    }
}
