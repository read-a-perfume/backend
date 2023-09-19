package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;

public interface FindPerfumeUseCase {

  PerfumeResult findPerfumeById(Long id);
}
