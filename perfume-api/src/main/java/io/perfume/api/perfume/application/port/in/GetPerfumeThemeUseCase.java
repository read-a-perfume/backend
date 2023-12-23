package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.PerfumeThemeResult;

public interface GetPerfumeThemeUseCase {
  PerfumeThemeResult getRecentTheme();
}
