package io.perfume.api.perfume.application.port.out;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface PerfumeQueryRepository {
  boolean existsPerfumeById(Long id);

  Optional<Perfume> findPerfumeById(Long id);

  NotePyramid getNotePyramidByPerfume(Long perfumeId);

  CustomSlice<SimplePerfumeResult> findPerfumesByBrand(
      Long brandId, Long lastPerfumeId, int pageSize);

  CustomPage<SimplePerfumeResult> findPerfumesByCategory(Long categoryId, Pageable pageable);

  CustomSlice<SimplePerfumeResult> findPerfumesByFavorite(Long lastPerfumeId, int pageSize);

  List<PerfumeNameResult> searchPerfumeByQuery(String query);
}
