package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.PerfumeFavorite;
import java.util.List;
import java.util.Optional;

public interface PerfumeFavoriteQueryRepository {

  Optional<PerfumeFavorite> findByUserAndPerfume(Long userId, Long perfumeId);

  List<PerfumeFavorite> findFavoritewedPerfumesByUser(Long userId);

}
