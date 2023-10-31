package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponseDto;
import io.perfume.api.perfume.adapter.in.http.dto.SimplePerfumeResponseDto;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
<<<<<<< HEAD
<<<<<<< HEAD
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
=======
import io.perfume.api.perfume.application.port.in.UserFollowPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.UserUnFollowPerfumeUseCase;
>>>>>>> 261faff ([RDPF-193] feat: Follow Service 추가)
=======
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
<<<<<<< HEAD
import io.perfume.api.perfume.application.port.in.UserUnFavoritePerfumeUseCase;
>>>>>>> a03dde0 ([RDPF-193] refactor: PerfumeFollow -> PerfumeFavorite 변경)
=======
>>>>>>> ff96a83 ([RDPF-193] feat: 서비스 로직 변경, api 추가)
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
  private final UserFavoritePerfumeUseCase userFavoritePerfumeUseCase;
=======
  private final UserFollowPerfumeUseCase userFollowPerfumeUseCase;

  private final UserUnFollowPerfumeUseCase userUnFollowPerfumeUseCase;
>>>>>>> 261faff ([RDPF-193] feat: Follow Service 추가)
=======
  private final UserFavoritePerfumeUseCase userFollowPerfumeUseCase;

  private final UserUnFavoritePerfumeUseCase userUnFollowPerfumeUseCase;
>>>>>>> a03dde0 ([RDPF-193] refactor: PerfumeFollow -> PerfumeFavorite 변경)
=======
  private final UserFavoritePerfumeUseCase userFavoritePerfumeUseCase;
>>>>>>> ff96a83 ([RDPF-193] feat: 서비스 로직 변경, api 추가)

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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/favorite/{id}")
  public void favoritePerfume(@AuthenticationPrincipal User user, @PathVariable Long id) {
    var userId = Long.parseLong(user.getUsername());
    userFavoritePerfumeUseCase.addAndDeleteFavoritePerfume(userId, id);
  }
=======
>>>>>>> 261faff ([RDPF-193] feat: Follow Service 추가)
=======
>>>>>>> a03dde0 ([RDPF-193] refactor: PerfumeFollow -> PerfumeFavorite 변경)

  @GetMapping
  public Slice<SimplePerfumeResponseDto> getPerfumesByBrand (@RequestParam Long
      brandId, @RequestParam @Nullable Long lastPerfumeId,
      @RequestParam int pageSize){
=======
  @GetMapping
  public Slice<SimplePerfumeResponseDto> getPerfumesByBrand(@RequestParam Long brandId, @RequestParam @Nullable Long lastPerfumeId,
                                                            @RequestParam int pageSize) {
>>>>>>> ff96a83 ([RDPF-193] feat: 서비스 로직 변경, api 추가)
    Slice<SimplePerfumeResult> perfumesByBrand = findPerfumeUseCase.findPerfumesByBrand(brandId,
        lastPerfumeId, pageSize);
    List<SimplePerfumeResponseDto> list = perfumesByBrand.getContent().stream()
        .map(SimplePerfumeResponseDto::of).toList();
    return new SliceImpl<>(list, perfumesByBrand.getPageable(), perfumesByBrand.hasNext());
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/favorite/{id}")
  public void favoritePerfume(@AuthenticationPrincipal User user, @PathVariable Long id) {
    var userId = Long.parseLong(user.getUsername());
    userFollowPerfumeUseCase.favoritePerfume(userId, perfumeId);
  }

  @PreAuthorize("isAuthenticated()")
  // @PostMapping
  public void unFollowPerfume(@AuthenticationPrincipal User user, @PathVariable Long perfumeId) {
    var userId = Long.parseLong(user.getUsername());
    userUnFollowPerfumeUseCase.unFavoritePerfume(userId, perfumeId);
    userFavoritePerfumeUseCase.addAndDeleteFavoritePerfume(userId, id);
  }
}
