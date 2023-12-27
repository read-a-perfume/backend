package io.perfume.api.perfume.application.service;

import io.perfume.api.file.application.exception.FileNotFoundException;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.perfume.application.exception.PerfumeThemeNotFoundException;
import io.perfume.api.perfume.application.port.in.GetPerfumeThemeUseCase;
import io.perfume.api.perfume.application.port.in.dto.PerfumeThemeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeThemeResult;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.application.port.out.PerfumeThemeQueryRepository;
import io.perfume.api.perfume.domain.PerfumeTheme;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPerfumeThemeService implements GetPerfumeThemeUseCase {

  private final PerfumeQueryRepository perfumeQueryRepository;
  private final PerfumeThemeQueryRepository perfumeThemeQueryRepository;
  private final FindFileUseCase findFileUseCase;

  public PerfumeThemeResult getRecentTheme() {
    PerfumeTheme perfumeTheme =
        perfumeThemeQueryRepository
            .getRecentTheme()
            .orElseThrow(PerfumeThemeNotFoundException::new);
    List<SimplePerfumeThemeResult> perfumes =
        perfumeQueryRepository.findPerfumesByIds(perfumeTheme.getPerfumeIds());
    String thumbnail =
        findFileUseCase
            .findFileById(perfumeTheme.getThumbnailId())
            .orElseThrow(() -> new FileNotFoundException(perfumeTheme.getThumbnailId()))
            .getUrl();
    return new PerfumeThemeResult(
        perfumeTheme.getTitle(), perfumeTheme.getContent(), thumbnail, perfumes);
  }
}
