package io.perfume.api.review.adapter.in.http;

import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewResponseDto;
import io.perfume.api.review.adapter.in.http.dto.DeleteReviewResponseDto;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.DeleteReviewUseCase;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewController {

  private final CreateReviewUseCase createReviewUseCase;

  private final DeleteReviewUseCase deleteReviewUseCase;

  public ReviewController(CreateReviewUseCase createReviewUseCase,
                          DeleteReviewUseCase deleteReviewUseCase) {
    this.createReviewUseCase = createReviewUseCase;
    this.deleteReviewUseCase = deleteReviewUseCase;
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
