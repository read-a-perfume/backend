package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.Review;

public record ReviewResult(
    Long id,
    String feeling,
    String situation,
    Long authorId
) {

  public static ReviewResult from(Review review) {
    return new ReviewResult(review.getId(), review.getFeeling(), review.getShortReview(), review.getUserId());
  }
}
