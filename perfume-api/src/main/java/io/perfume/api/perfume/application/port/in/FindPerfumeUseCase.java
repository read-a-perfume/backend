package io.perfume.api.perfume.application.port.in;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface FindPerfumeUseCase {

  PerfumeResult findPerfumeById(Long id);

  CustomSlice<SimplePerfumeResult> findPerfumesByBrand(Long brandId, Long lastPerfumeId, int pageSize);

  CustomPage<SimplePerfumeResult> findPerfumesByCategory(Long categoryId, Pageable pageable);

  List<PerfumeNameResult> searchPerfumeByQuery(String query);
}
