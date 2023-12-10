package io.perfume.api.perfume.application.service;

import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.perfume.application.exception.PerfumeNotFoundException;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.application.in.review.ReviewStatisticUseCase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPerfumeService implements FindPerfumeUseCase {

  private final PerfumeQueryRepository perfumeQueryRepository;
  private final FindCategoryUseCase findCategoryUseCase;
  private final FindBrandUseCase findBrandUseCase;
  private final FindFileUseCase findFileUseCase;
  private final ReviewStatisticUseCase reviewStatisticUseCase;

  @Override
  public PerfumeResult findPerfumeById(Long id) {
    Perfume perfume =
        perfumeQueryRepository
            .findPerfumeById(id)
            .orElseThrow(() -> new PerfumeNotFoundException(id));
    CategoryResult categoryResult = findCategoryUseCase.findCategoryById(perfume.getCategoryId());
    BrandForPerfumeResult brandResult = findBrandUseCase.findBrandForPerfume(perfume.getBrandId());
    NotePyramid notePyramid = perfumeQueryRepository.getNotePyramidByPerfume(perfume.getId());
    Optional<File> fileById = findFileUseCase.findFileById(perfume.getThumbnailId());
    String thumbnail = "";
    if (fileById.isPresent()) {
      thumbnail = fileById.get().getUrl();
    }

    return PerfumeResult.from(perfume, categoryResult, brandResult, thumbnail, notePyramid);
  }

  @Override
  public CustomSlice<SimplePerfumeResult> findPerfumesByBrand(
      Long brandId, Long lastPerfumeId, int pageSize) {
    return perfumeQueryRepository.findPerfumesByBrand(brandId, lastPerfumeId, pageSize);
  }

  @Override
  public CustomPage<SimplePerfumeResult> findPerfumesByCategory(
      Long categoryId, Pageable pageable) {
    return perfumeQueryRepository.findPerfumesByCategory(categoryId, pageable);
  }

  @Override
  public CustomSlice<SimplePerfumeResult> findPerfumesByFavorite(Long lastPerfumeId, int pageSize) {
    return perfumeQueryRepository.findPerfumesByFavorite(lastPerfumeId, pageSize);
  }

  @Override
  public List<PerfumeNameResult> searchPerfumeByQuery(String query) {
    return perfumeQueryRepository.searchPerfumeByQuery(query);
  }

  @Override
  public ReviewStatisticResult getStatistics(Long id) {
    if (!perfumeQueryRepository.existsPerfumeById(id)) {
      throw new PerfumeNotFoundException(id);
    }
    return reviewStatisticUseCase.getStatisticByPerfume(id);
  }
}
