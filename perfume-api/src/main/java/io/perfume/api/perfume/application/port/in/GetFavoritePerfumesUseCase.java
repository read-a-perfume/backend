package io.perfume.api.perfume.application.port.in;

import io.perfume.api.perfume.application.port.in.dto.PerfumeFavoriteResult;
import java.util.List;

public interface GetFavoritePerfumesUseCase {

  List<PerfumeFavoriteResult> getFavoritePerfumes(Long authorId);
}
