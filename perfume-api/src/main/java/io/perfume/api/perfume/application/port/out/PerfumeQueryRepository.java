package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import java.util.Optional;

public interface PerfumeQueryRepository {

  Optional<Perfume> findPerfumeById(Long id);

  NotePyramid getNotePyramidByPerfume(Long perfumeId);
}
