package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.PerfumeFollow;
import java.util.List;
import java.util.Optional;

public interface PerfumeFollowQueryRepository {

  Optional<PerfumeFollow> findByUserAndPerfume(Long userId, Long perfumeId);

  List<PerfumeFollow> findFollowedPerfumesByUser(Long userId);

}
