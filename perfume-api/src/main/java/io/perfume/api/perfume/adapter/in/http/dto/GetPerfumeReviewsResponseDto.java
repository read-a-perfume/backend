package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.review.adapter.in.http.dto.ReviewUser;
import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import java.util.List;

public record GetPerfumeReviewsResponseDto(
    Long id,
    String shortReview,
    ReviewUser user,
    List<String> keywords,
    List<String> thumbnails,
    long likeCount,
    long commentCount) {

  public static List<GetPerfumeReviewsResponseDto> from(List<ReviewDetailResult> results) {
    return results.stream()
        .map(
            result ->
                new GetPerfumeReviewsResponseDto(
                    result.id(),
                    result.shortReview(),
                    new ReviewUser(result.user().id(), result.user().name(), ""),
                    result.tags(),
                    List.of(),
                    result.likeCount(),
                    result.commentCount()))
        .toList();
  }
}
