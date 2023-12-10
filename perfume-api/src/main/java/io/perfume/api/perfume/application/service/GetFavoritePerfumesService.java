package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.application.port.in.GetFavoritePerfumesUseCase;
import io.perfume.api.perfume.application.port.in.dto.PerfumeFavoriteResult;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteQueryRepository;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.perfume.domain.PerfumeFavorite;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetFavoritePerfumesService implements GetFavoritePerfumesUseCase {

  private final PerfumeFavoriteQueryRepository perfumeFavoriteQueryRepository;

  private final PerfumeQueryRepository perfumeQueryRepository;

  public GetFavoritePerfumesService(
      PerfumeFavoriteQueryRepository perfumeFavoriteQueryRepository,
      PerfumeQueryRepository perfumeQueryRepository) {
    this.perfumeFavoriteQueryRepository = perfumeFavoriteQueryRepository;
    this.perfumeQueryRepository = perfumeQueryRepository;
  }

  @Override
  public List<PerfumeFavoriteResult> getFavoritePerfumes(Long authorId) {
    System.out.println(
        perfumeFavoriteQueryRepository.findFavoritePerfumesByUserId(authorId).size());
    List<PerfumeFavorite> find =
        perfumeFavoriteQueryRepository.findFavoritePerfumesByUserId(authorId);

    List<Long> perfume_id =
        find.stream().map(perfumeFavorite -> perfumeFavorite.getPerfumeId()).toList();

    for (Long id : perfume_id) {
      System.out.println(id);
    }

    System.out.println(perfumeQueryRepository.findPerfumeById(1L).get().getName());
    System.out.println(perfumeQueryRepository.findPerfumeById(2L).get().getName());
    System.out.println(perfumeQueryRepository.findPerfumeById(3L).get().getName());

    List<Perfume> perfumes =
        perfume_id.stream()
            .map(id -> perfumeQueryRepository.findPerfumeById(id).orElseThrow())
            .toList();

    for (Perfume perfume : perfumes) {
      System.out.println(perfume.getName());
    }

    return perfumeFavoriteQueryRepository.findFavoritePerfumesByUserId(authorId).stream()
        .map(
            perfumeFavorite ->
                perfumeQueryRepository
                    .findPerfumeById(perfumeFavorite.getPerfumeId())
                    .orElseThrow()
                    .getName())
        .map(PerfumeFavoriteResult::new)
        .toList();
  }
}
