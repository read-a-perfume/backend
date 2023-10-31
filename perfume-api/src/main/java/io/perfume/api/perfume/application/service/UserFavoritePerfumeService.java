package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.application.exception.PerfumeFavoriteNotFoundException;
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteQueryRepository;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteRepository;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFavoritePerfumeService implements UserFavoritePerfumeUseCase {

  private final PerfumeFavoriteRepository perfumeFavoriteRepository;
  private final PerfumeFavoriteQueryRepository perfumeFavoriteQueryRepository;

  public UserFavoritePerfumeService(PerfumeFavoriteRepository perfumeFavoriteRepository,
      PerfumeFavoriteQueryRepository perfumeFavoriteQueryRepository) {
    this.perfumeFavoriteRepository = perfumeFavoriteRepository;
    this.perfumeFavoriteQueryRepository = perfumeFavoriteQueryRepository;
  }


  @Override
  public void addAndDeleteFavoritePerfume(Long authorId, Long perfumeId) {
    Optional<PerfumeFavorite> foundPerfumeFollow =
        perfumeFavoriteQueryRepository.findByUserAndPerfume(authorId, perfumeId);

    if (foundPerfumeFollow.isPresent()) {
      deleteFavoritePerfume(foundPerfumeFollow.get());
      return;
    }
    addFavoritePerfume(authorId, perfumeId);
  }

  private void addFavoritePerfume(Long authorId, Long perfumeId) {
    LocalDateTime now = LocalDateTime.now();
    PerfumeFavorite perfumeFavorite = PerfumeFavorite.create(authorId, perfumeId, now);
    perfumeFavoriteRepository.save(perfumeFavorite);
  }

  private void deleteFavoritePerfume(PerfumeFavorite foundPerfumeFavorite) {
    LocalDateTime now = LocalDateTime.now();
    foundPerfumeFavorite.markDelete(now);
    perfumeFavoriteRepository.save(foundPerfumeFavorite);
  }
}
