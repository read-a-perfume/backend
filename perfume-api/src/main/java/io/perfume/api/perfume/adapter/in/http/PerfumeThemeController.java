package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.in.http.dto.PerfumeThemeResponseDto;
import io.perfume.api.perfume.application.port.in.GetPerfumeThemeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/perfume-themes")
@RequiredArgsConstructor
public class PerfumeThemeController {

  private final GetPerfumeThemeUseCase getPerfumeThemeUseCase;

  @GetMapping("/recent")
  public PerfumeThemeResponseDto getRecentTheme() {
    return PerfumeThemeResponseDto.from(getPerfumeThemeUseCase.getRecentTheme());
  }
}
