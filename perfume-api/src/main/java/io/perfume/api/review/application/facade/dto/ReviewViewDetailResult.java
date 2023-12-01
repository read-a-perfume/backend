package io.perfume.api.review.application.facade.dto;

import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.util.List;

public record ReviewViewDetailResult(
    Long id,
    String shortReview,
    String fullReview,
    DayType dayType,
    Strength strength,
    Season season,
    Duration duration,
    List<String> tags,
    List<String> images,
    Long perfumeId,
    ReviewDetailUserResult author,
    long likeCount,
    long commentCount
) {

  public static ReviewViewDetailResult from(ReviewResult review, UserResult user,
                                            List<String> tags, List<String> images, long likeCount,
                                            long commentCount) {
    final var reviewDetailUserResult = ReviewDetailUserResult.from(user);

    return new ReviewViewDetailResult(
        review.id(),
        review.shortReview(),
        review.fullReview(),
        review.dayType(),
        review.strength(),
        review.season(),
        review.duration(),
        tags,
        images,
        review.perfumeId(),
        reviewDetailUserResult,
        likeCount,
        commentCount);
  }
}
