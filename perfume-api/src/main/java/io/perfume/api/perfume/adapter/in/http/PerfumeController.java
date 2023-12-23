package io.perfume.api.perfume.adapter.in.http;

import io.micrometer.common.lang.Nullable;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.perfume.adapter.in.http.dto.*;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.GetFavoritePerfumesUseCase;
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.CreatePerfumeCommand;
import io.perfume.api.perfume.application.port.in.dto.PerfumeFavoriteResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import io.perfume.api.review.application.in.review.GetPerfumeReviewsUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  private final GetPerfumeReviewsUseCase getPerfumeReviewsUseCase;

  @GetMapping("/{id}")
  public PerfumeResponseDto findPerfumeById(@PathVariable Long id) {
    return PerfumeResponseDto.from(findPerfumeUseCase.findPerfumeById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerfume(@RequestBody CreatePerfumeRequestDto createPerfumeRequestDto) {
    CreatePerfumeCommand createPerfumeCommand = createPerfumeRequestDto.toCommand();

    createPerfumeUseCase.createPerfume(createPerfumeCommand);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/favorite/{id}")
  public ResponseEntity<FavoritePerfumeResponseDto> favoritePerfume(
      @AuthenticationPrincipal User user, @PathVariable("id") Long perfumeId) {
    var userId = Long.parseLong(user.getUsername());
    userFavoritePerfumeUseCase.addAndDeleteFavoritePerfume(userId, perfumeId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new FavoritePerfumeResponseDto(userId, perfumeId));
  }

  @GetMapping("/category/{id}")
  public CustomPage<SimplePerfumeResponseDto> getPerfumesByCategory(
      @PathVariable Long id, Pageable pageable) {
    CustomPage<SimplePerfumeResult> perfumesByCategory =
        findPerfumeUseCase.findPerfumesByCategory(id, pageable);
    List<SimplePerfumeResponseDto> list =
        perfumesByCategory.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new CustomPage<>(list, perfumesByCategory);
  }

  @GetMapping
  public CustomSlice<SimplePerfumeResponseDto> getPerfumesByCursorPaging(
      @RequestParam String sort,
      @RequestParam @Nullable Long lastPerfumeId,
      @RequestParam int pageSize,
      @RequestParam @Nullable Long brandId) {
    PerfumeSort perfumeSort = PerfumeSort.fromString(sort);
    if (perfumeSort == PerfumeSort.BRAND) {
      if (brandId == null) {
        throw new IllegalArgumentException("brand 기준으로 정렬하려면 brandId가 필요합니다.");
      }
      return getPerfumesByBrand(brandId, lastPerfumeId, pageSize);
    }
    if (perfumeSort == PerfumeSort.FAVORITE) {
      return getPerfumesByFavorite(lastPerfumeId, pageSize);
    }
    throw new IllegalArgumentException("정렬 기준으로는 brand, favorite만 가능합니다.");
  }

  private CustomSlice<SimplePerfumeResponseDto> getPerfumesByBrand(
      Long brandId, Long lastPerfumeId, int pageSize) {
    CustomSlice<SimplePerfumeResult> perfumesByBrand =
        findPerfumeUseCase.findPerfumesByBrand(brandId, lastPerfumeId, pageSize);
    List<SimplePerfumeResponseDto> list =
        perfumesByBrand.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new CustomSlice<>(list, perfumesByBrand.isHasNext());
  }

  private CustomSlice<SimplePerfumeResponseDto> getPerfumesByFavorite(
      Long lastPerfumeId, int pageSize) {
    CustomSlice<SimplePerfumeResult> perfumesByFavorite =
        findPerfumeUseCase.findPerfumesByFavorite(lastPerfumeId, pageSize);
    List<SimplePerfumeResponseDto> list =
        perfumesByFavorite.getContent().stream().map(SimplePerfumeResponseDto::of).toList();
    return new CustomSlice<>(list, perfumesByFavorite.isHasNext());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/favorites")
  public List<PerfumeFavoriteResponseDto> getFavoritePerfumes(@AuthenticationPrincipal User user) {
    var userId = Long.parseLong(user.getUsername());
    List<PerfumeFavoriteResult> favoritePerfumes =
        getFavoritePerfumesUseCase.getFavoritePerfumes(userId);

    return PerfumeFavoriteResponseDto.from(favoritePerfumes);
  }

  @GetMapping("/search")
  public List<PerfumeNameResponseDto> searchPerfumeByQuery(@RequestParam String query) {
    List<PerfumeNameResult> perfumesByQuery = findPerfumeUseCase.searchPerfumeByQuery(query);
    return perfumesByQuery.stream().map(PerfumeNameResponseDto::of).toList();
  }

  @GetMapping("/{id}/statistics")
  public PerfumeStatisticResponseDto getStatistics(@PathVariable Long id) {
    return PerfumeStatisticResponseDto.from(findPerfumeUseCase.getStatistics(id));
  }

  @GetMapping("/{id}/reviews")
  public CustomPage<GetPerfumeReviewsResponseDto> getPerfumeReviews(
      @PathVariable Long id, @RequestParam int page, @RequestParam int size) {
    final CustomPage<ReviewDetailResult> reviews =
        getPerfumeReviewsUseCase.getPaginatedPerfumeReviews(id, page, size);
    final List<GetPerfumeReviewsResponseDto> reviewResults =
        GetPerfumeReviewsResponseDto.from(reviews.getContent());

    return new CustomPage<>(reviewResults, reviews);
  }
}
