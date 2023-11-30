package io.perfume.api.review.application.facade.dto;

import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.List;

public record ReviewViewDetailResult(
    Long id,
    String feeling,
    String situation,
    List<String> tags,
    List<String> images,
    ReviewDetailUserResult author,
    long likeCount,
    long commentCount
) {

  public static ReviewViewDetailResult from(ReviewResult review, UserResult user,
                                            List<String> tags, List<String> images, long likeCount,
                                            long commentCount) {
    final var reviewDetailUserResult = ReviewDetailUserResult.from(user);

    return new ReviewViewDetailResult(review.id(), review.feeling(), review.situation(), tags,
        images,
        reviewDetailUserResult, likeCount, commentCount);
  }
}
