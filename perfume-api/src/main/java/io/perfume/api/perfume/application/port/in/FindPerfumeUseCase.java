package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FindPerfumeUseCase {

  PerfumeResult findPerfumeById(Long id);

  Slice<SimplePerfumeResult> findPerfumesByBrand(Long brandId, Long lastPerfumeId, int pageSize);

  Page<SimplePerfumeResult> findPerfumesByCategory(Long categoryId, Pageable pageable);
}
