package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import org.springframework.data.domain.Slice;

public interface FindPerfumeUseCase {

  PerfumeResult findPerfumeById(Long id);

  Slice<SimplePerfumeResult> findPerfumeByCategory(Long categoryId, Long lastPerfumeId, int limit);
}
