package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeFavoriteResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeNameResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.SimplePerfumeResponseDto;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.GetFavoritePerfumesUseCase;
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.application.port.in.dto.PerfumeFavoriteResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

  private final UserFavoritePerfumeUseCase userFavoritePerfumeUseCase;

  private final GetFavoritePerfumesUseCase getFavoritePerfumesUseCase;

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


  @PreAuthorize("isAuthenticated()")
  @PostMapping("/favorite/{id}")
  public void favoritePerfume(@AuthenticationPrincipal User user, @PathVariable("id") Long perfumeId) {
    var userId = Long.parseLong(user.getUsername());
    userFavoritePerfumeUseCase.addAndDeleteFavoritePerfume(userId, perfumeId);
  }


  @GetMapping("/category/{id}")
  public Page<SimplePerfumeResponseDto> getPerfumesByCategory(@PathVariable Long id, Pageable pageable) {
    Page<SimplePerfumeResult> perfumesByCategory = findPerfumeUseCase.findPerfumesByCategory(id, pageable);
    List<SimplePerfumeResponseDto> list = perfumesByCategory.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new PageImpl<>(list, perfumesByCategory.getPageable(), perfumesByCategory.getTotalElements());
  }
  
  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public List<PerfumeFavoriteResponseDto> getFavoritePerfumes(@AuthenticationPrincipal User user) {
    var userId = Long.parseLong(user.getUsername());
    List<PerfumeFavoriteResult> favoritePerfumes = getFavoritePerfumesUseCase
        .getFavoritePerfumes(userId);

    return PerfumeFavoriteResponseDto.from(favoritePerfumes);
  }

  @GetMapping("/search")
  public List<PerfumeNameResponseDto> searchPerfumeByQuery(@RequestParam String query) {
    List<PerfumeNameResult> perfumesByQuery = findPerfumeUseCase.searchPerfumeByQuery(query);
    return perfumesByQuery.stream().map(PerfumeNameResponseDto::of).toList();
  }
}
