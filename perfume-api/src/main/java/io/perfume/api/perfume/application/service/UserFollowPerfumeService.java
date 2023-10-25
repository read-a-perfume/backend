package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.application.port.in.UserFollowPerfumeUseCase;
import io.perfume.api.perfume.application.port.out.PerfumeFollowQueryRepository;
import io.perfume.api.perfume.application.port.out.PerfumeFollowRepository;
import io.perfume.api.perfume.domain.PerfumeFollow;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class UserFollowPerfumeService implements UserFollowPerfumeUseCase {

  private final PerfumeFollowRepository perfumeFollowRepository;
  private final PerfumeFollowQueryRepository perfumeFollowQueryRepository;

  public UserFollowPerfumeService(PerfumeFollowRepository perfumeFollowRepository,
      PerfumeFollowQueryRepository perfumeFollowQueryRepository) {
    this.perfumeFollowRepository = perfumeFollowRepository;
    this.perfumeFollowQueryRepository = perfumeFollowQueryRepository;
  }

  @Override
  @Transactional
  public void followPerfume(Long authorId, Long perfumeId) {
    Optional<PerfumeFollow> foundPerfumeFollow =
        perfumeFollowQueryRepository.findByUserAndPerfume(authorId, perfumeId);

    userFollowPerfume(authorId, perfumeId, foundPerfumeFollow);
  }

  private void userFollowPerfume(Long authorId, Long perfumeId,
      Optional<PerfumeFollow> foundPerfumeFollow) {
    if (foundPerfumeFollow.isPresent()) {
      LocalDateTime now = LocalDateTime.now();
      PerfumeFollow perfumeFollow = PerfumeFollow.create(authorId, perfumeId, now);
      perfumeFollowRepository.save(perfumeFollow);
    }
  }
}
