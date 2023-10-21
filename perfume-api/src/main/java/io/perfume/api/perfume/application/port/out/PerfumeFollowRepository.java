package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.perfume.domain.PerfumeFollow;

public interface PerfumeFollowRepository {
  PerfumeFollow save(PerfumeFollow perfumeFollow);
}
