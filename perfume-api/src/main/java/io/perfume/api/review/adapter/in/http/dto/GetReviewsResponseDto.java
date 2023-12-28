package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import java.util.Collections;
import java.util.List;

public record GetReviewsResponseDto(
    Long id,
    String shortReview,
    ReviewUser author,
    List<String> keywords,
    List<String> thumbnails,
    long likeCount,
    long commentCount) {

  public static GetReviewsResponseDto from(final ReviewDetailResult result) {
    return new GetReviewsResponseDto(
        result.id(),
        result.shortReview(),
        new ReviewUser(result.user().id(), result.user().name(), ""),
        result.tags(),
        Collections.emptyList(),
        result.likeCount(),
        result.commentCount());
  }
}
