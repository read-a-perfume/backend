package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.Magazine;
import java.util.Optional;

public interface MagazineQueryRepository {

  Optional<Magazine> findById(Long id);
}
