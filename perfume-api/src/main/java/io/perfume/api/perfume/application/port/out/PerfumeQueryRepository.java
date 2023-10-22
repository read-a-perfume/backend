package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.Optional;
import org.springframework.data.domain.Slice;

public interface PerfumeQueryRepository {

  Optional<Perfume> findPerfumeById(Long id);

  NotePyramid getNotePyramidByPerfume(Long perfumeId);

  Slice<SimplePerfumeResult> findPerfumeByCategory(Long categoryId, Long lastPerfumeId, int limit);
}
