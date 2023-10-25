package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.application.exception.PerfumeFollowNotFoundException;
import io.perfume.api.perfume.application.port.in.UserUnFollowPerfumeUseCase;
import io.perfume.api.perfume.application.port.out.PerfumeFollowQueryRepository;
import io.perfume.api.perfume.application.port.out.PerfumeFollowRepository;
import io.perfume.api.perfume.domain.PerfumeFollow;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

public class UserUnFollowPerfumeService implements UserUnFollowPerfumeUseCase {

  private final PerfumeFollowRepository perfumeFollowRepository;
  private final PerfumeFollowQueryRepository perfumeFollowQueryRepository;

  public UserUnFollowPerfumeService(PerfumeFollowRepository perfumeFollowRepository,
      PerfumeFollowQueryRepository perfumeFollowQueryRepository) {
    this.perfumeFollowRepository = perfumeFollowRepository;
    this.perfumeFollowQueryRepository = perfumeFollowQueryRepository;
  }

  @Override
  @Transactional
  public void unFollowPerfume(Long authorId, Long perfumeId) {
    PerfumeFollow foundPerfumeFollow = perfumeFollowQueryRepository
        .findByUserAndPerfume(authorId, perfumeId)
        .orElseThrow(() -> new PerfumeFollowNotFoundException(authorId, perfumeId));

    userUnFollowPerfume(foundPerfumeFollow);
  }

  private void userUnFollowPerfume(PerfumeFollow foundPerfumeFollow) {
    LocalDateTime now = LocalDateTime.now();
    foundPerfumeFollow.markDelete(now);
    perfumeFollowRepository.save(foundPerfumeFollow);
  }
}
