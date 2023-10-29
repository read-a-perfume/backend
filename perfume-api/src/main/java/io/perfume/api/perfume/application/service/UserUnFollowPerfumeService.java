package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.application.exception.PerfumeFavoriteNotFoundException;
import io.perfume.api.perfume.application.port.in.UserUnFavoritePerfumeUseCase;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteQueryRepository;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteRepository;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserUnFollowPerfumeService implements UserUnFavoritePerfumeUseCase {

  private final PerfumeFavoriteRepository perfumeFavoriteRepository;
  private final PerfumeFavoriteQueryRepository perfumeFavoriteQueryRepository;

  public UserUnFollowPerfumeService(PerfumeFavoriteRepository perfumeFavoriteRepository,
      PerfumeFavoriteQueryRepository perfumeFavoriteQueryRepository) {
    this.perfumeFavoriteRepository = perfumeFavoriteRepository;
    this.perfumeFavoriteQueryRepository = perfumeFavoriteQueryRepository;
  }

  @Override
  @Transactional
  public void unFavoritePerfume(Long authorId, Long perfumeId) {
    PerfumeFavorite foundPerfumeFavorite = perfumeFavoriteQueryRepository
        .findByUserAndPerfume(authorId, perfumeId)
        .orElseThrow(() -> new PerfumeFavoriteNotFoundException(authorId, perfumeId));

    userUnFavoritePerfume(foundPerfumeFavorite);
  }

  private void userUnFavoritePerfume(PerfumeFavorite foundPerfumeFavorite) {
    LocalDateTime now = LocalDateTime.now();
    foundPerfumeFavorite.markDelete(now);
    perfumeFavoriteRepository.save(foundPerfumeFavorite);
  }
}
