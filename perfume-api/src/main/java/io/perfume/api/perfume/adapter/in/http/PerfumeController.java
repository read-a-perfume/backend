package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeNameResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.SimplePerfumeResponseDto;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/perfumes")
@RequiredArgsConstructor
public class PerfumeController {

  private final FindPerfumeUseCase findPerfumeUseCase;

  private final CreatePerfumeUseCase createPerfumeUseCase;

  @GetMapping("/{id}")
  public PerfumeResponseDto findPerfumeById(@PathVariable Long id) {
    return PerfumeResponseDto.of(findPerfumeUseCase.findPerfumeById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerfume(@RequestBody CreatePerfumeRequestDto createPerfumeRequestDto) {
    CreatePerfumeCommand createPerfumeCommand = createPerfumeRequestDto.toCommand();

    createPerfumeUseCase.createPerfume(createPerfumeCommand);
  }

  @GetMapping
  public CustomSlice<SimplePerfumeResponseDto> getPerfumesByBrand(@RequestParam Long brandId, @RequestParam @Nullable Long lastPerfumeId,
                                                                  @RequestParam int pageSize) {
    CustomSlice<SimplePerfumeResult> perfumesByBrand = findPerfumeUseCase.findPerfumesByBrand(brandId, lastPerfumeId, pageSize);
    List<SimplePerfumeResponseDto> list = perfumesByBrand.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new CustomSlice<>(list, perfumesByBrand.isHasNext());
  }

  @GetMapping("/category/{id}")
  public CustomPage<SimplePerfumeResponseDto> getPerfumesByCategory(@PathVariable Long id, Pageable pageable) {
    CustomPage<SimplePerfumeResult> perfumesByCategory = findPerfumeUseCase.findPerfumesByCategory(id, pageable);
    List<SimplePerfumeResponseDto> list = perfumesByCategory.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new CustomPage<>(list, perfumesByCategory);
  }

  @GetMapping("/search")
  public List<PerfumeNameResponseDto> searchPerfumeByQuery(@RequestParam String query) {
    List<PerfumeNameResult> perfumesByQuery = findPerfumeUseCase.searchPerfumeByQuery(query);
    return perfumesByQuery.stream().map(PerfumeNameResponseDto::of).toList();
  }
}
