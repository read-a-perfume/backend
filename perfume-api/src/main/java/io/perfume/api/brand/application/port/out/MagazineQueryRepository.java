package io.perfume.api.brand.application.port.out;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.brand.domain.Magazine;
import java.util.List;
import java.util.Optional;

public interface MagazineQueryRepository {

  Optional<Magazine> findById(Long id);

  List<Magazine> findByMagazines(Long brandId);

  CursorPagination<Magazine> findByBrandId(CursorPageable pageable, Long brandId);
}
