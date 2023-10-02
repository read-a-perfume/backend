package io.perfume.api.perfume.application.service;

import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.perfume.application.exception.PerfumeNotFoundException;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.NotePyramid;
import io.perfume.api.perfume.domain.Perfume;
import lombok.RequiredArgsConstructor;
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
}