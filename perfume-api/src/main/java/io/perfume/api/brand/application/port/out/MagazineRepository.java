package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.Magazine;

public interface MagazineRepository {

  Magazine save(Magazine magazine);
}
