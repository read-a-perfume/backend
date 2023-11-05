package io.perfume.api.perfume.application.service;

import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPerfumeService implements FindPerfumeUseCase {

  private final PerfumeQueryRepository perfumeQueryRepository;
  private final FindCategoryUseCase findCategoryUseCase;
  private final FindBrandUseCase findBrandUseCase;

  @Override
  public PerfumeResult findPerfumeById(Long id) {
    Perfume perfume = perfumeQueryRepository.findPerfumeById(id).orElseThrow(() -> new PerfumeNotFoundException(id));
    CategoryResult categoryResult = findCategoryUseCase.findCategoryById(perfume.getCategoryId());
    BrandForPerfumeResult brandResult = findBrandUseCase.findBrandForPerfume(perfume.getBrandId());
    NotePyramid notePyramid = perfumeQueryRepository.getNotePyramidByPerfume(perfume.getId());
    // TODO: file의 thumbnail을 얻어와야 한다.

    return PerfumeResult.from(perfume, categoryResult, brandResult, notePyramid);
  }

  @Override
  public Slice<SimplePerfumeResult> findPerfumesByBrand(Long brandId, Long lastPerfumeId, int pageSize) {
    return perfumeQueryRepository.findPerfumesByBrand(brandId, lastPerfumeId, pageSize);
  }

  @Override
  public Page<SimplePerfumeResult> findPerfumesByCategory(Long categoryId, Pageable pageable) {
    return perfumeQueryRepository.findPerfumesByCategory(categoryId, pageable);
  }

  @Override
  public List<PerfumeNameResult> searchPerfumeByQuery(String query) {
    return perfumeQueryRepository.searchPerfumeByQuery(query);
  }
}
