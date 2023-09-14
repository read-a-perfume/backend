package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import java.io.FileNotFoundException;

public interface GetPerfumeUseCase {

  PerfumeResult getPerfume(Long id);
}
