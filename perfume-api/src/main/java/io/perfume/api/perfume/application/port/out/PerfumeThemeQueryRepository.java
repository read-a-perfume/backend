package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.PerfumeTheme;
import java.util.Optional;

public interface PerfumeThemeQueryRepository {
  Optional<PerfumeTheme> getRecentTheme();
}
