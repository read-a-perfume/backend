package io.perfume.api.review.application.facade.dto;

import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.List;

public record ReviewDetailResult(
    Long id,
    String shortReview,
    ReviewDetailUserResult user,
    List<String> tags,
    long likeCount,
    long commentCount
) {

  public static ReviewDetailResult from(ReviewResult review, UserResult user, List<String> tags, long likeCount, long commentCount) {
    final var reviewDetailUserResult = ReviewDetailUserResult.from(user);

    return new ReviewDetailResult(review.id(), review.shortReview(), reviewDetailUserResult, tags, likeCount, commentCount);
  }
}
