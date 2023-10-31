package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.SimplePerfumeResponseDto;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
  public Slice<SimplePerfumeResponseDto> getPerfumesByBrand(@RequestParam Long brandId, @RequestParam @Nullable Long lastPerfumeId,
                                                            @RequestParam int pageSize) {
    Slice<SimplePerfumeResult> perfumesByBrand = findPerfumeUseCase.findPerfumesByBrand(brandId, lastPerfumeId, pageSize);
    List<SimplePerfumeResponseDto> list = perfumesByBrand.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new SliceImpl<>(list, perfumesByBrand.getPageable(), perfumesByBrand.hasNext());
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/favorite/{id}")
  public void favoritePerfume(@AuthenticationPrincipal User user, @PathVariable Long id) {
    var userId = Long.parseLong(user.getUsername());
    userFavoritePerfumeUseCase.addAndDeleteFavoritePerfume(userId, id);
  }

}
