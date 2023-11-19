package io.perfume.api.perfume.application.port.out;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PerfumeQueryRepository {

  Optional<Perfume> findPerfumeById(Long id);

  NotePyramid getNotePyramidByPerfume(Long perfumeId);

  Slice<SimplePerfumeResult> findPerfumesByBrand(Long brandId, Long lastPerfumeId, int pageSize);

  CustomPage<SimplePerfumeResult> findPerfumesByCategory(Long categoryId, Pageable pageable);

  List<PerfumeNameResult> searchPerfumeByQuery(String query);
}
