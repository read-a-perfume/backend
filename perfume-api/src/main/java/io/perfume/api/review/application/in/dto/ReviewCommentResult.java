package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewComment;

public record ReviewCommentResult(
    Long id,
    Long userId,
    Long reviewId,
    String content
) {

  public static ReviewCommentResult from(ReviewComment reviewComment) {
    return new ReviewCommentResult(
        reviewComment.getId(),
        reviewComment.getUserId(),
        reviewComment.getReviewId(),
        reviewComment.getContent()
    );
  }
}
