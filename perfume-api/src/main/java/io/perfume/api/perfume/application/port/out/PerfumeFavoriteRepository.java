package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.PerfumeFavorite;

public interface PerfumeFavoriteRepository {
  PerfumeFavorite save(PerfumeFavorite perfumeFavorite);
}
