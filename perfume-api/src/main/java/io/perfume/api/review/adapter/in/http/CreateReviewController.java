package io.perfume.api.review.adapter.in.http;

import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateReviewController {

  private final CreateReviewUseCase createReviewUseCase;

  public CreateReviewController(CreateReviewUseCase createReviewUseCase) {
    this.createReviewUseCase = createReviewUseCase;
  }

  @PostMapping("/reviews")
  public void createReview(
      @AuthenticationPrincipal User user,
      @RequestBody CreateReviewRequestDto requestDto
  ) {
    var userId =  Long.parseLong(user.getUsername());
    var response = createReviewUseCase.create(userId, requestDto.toCommand());
  }
}
