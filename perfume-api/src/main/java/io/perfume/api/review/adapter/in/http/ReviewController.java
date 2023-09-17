package io.perfume.api.review.adapter.in.http;

import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewResponseDto;
import io.perfume.api.review.adapter.in.http.dto.DeleteReviewResponseDto;
import io.perfume.api.review.adapter.in.http.dto.GetReviewsRequestDto;
import io.perfume.api.review.adapter.in.http.dto.GetReviewsResponseDto;
import io.perfume.api.review.application.facade.ReviewDetailFacadeService;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.DeleteReviewUseCase;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewController {

  private final CreateReviewUseCase createReviewUseCase;

  private final DeleteReviewUseCase deleteReviewUseCase;

  private final ReviewDetailFacadeService reviewDetailFacadeService;

  public ReviewController(CreateReviewUseCase createReviewUseCase,
                          DeleteReviewUseCase deleteReviewUseCase,
                          ReviewDetailFacadeService reviewDetailFacadeService
  ) {
    this.createReviewUseCase = createReviewUseCase;
    this.deleteReviewUseCase = deleteReviewUseCase;
    this.reviewDetailFacadeService = reviewDetailFacadeService;
  }

  @GetMapping
  public List<GetReviewsResponseDto> getReviews(
      GetReviewsRequestDto dto
  ) {
    final var results = reviewDetailFacadeService.getPaginatedReviews(dto.offset(), dto.limit());

    return GetReviewsResponseDto.from(results);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  public CreateReviewResponseDto createReview(
      @AuthenticationPrincipal User user,
      @RequestBody CreateReviewRequestDto requestDto
  ) {
    var userId = Long.parseLong(user.getUsername());
    var response = createReviewUseCase.create(userId, requestDto.toCommand());

    return new CreateReviewResponseDto(response.id());
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteReviewResponseDto> deleteReview(
      @AuthenticationPrincipal User user,
      @PathVariable Long id
  ) {
    var now = LocalDateTime.now();
    var userId = Long.parseLong(user.getUsername());
    deleteReviewUseCase.delete(userId, id, now);

    return ResponseEntity.ok(new DeleteReviewResponseDto(id));
  }
}
