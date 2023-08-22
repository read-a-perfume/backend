package io.perfume.api.brand.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<BrandEntity, Long> {
}
