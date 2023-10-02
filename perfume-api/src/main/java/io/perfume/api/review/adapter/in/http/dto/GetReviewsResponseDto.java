package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import java.util.List;

public record GetReviewsResponseDto(
    Long id,
    String feeling,
    ReviewUser user,
    List<String> tags
) {

  public static List<GetReviewsResponseDto> from(List<ReviewDetailResult> results) {
    return results
        .stream()
        .map(result ->
            new GetReviewsResponseDto(
                result.id(),
                result.feeling(),
                new ReviewUser(result.user().id(), result.user().name(), ""),
                result.tags())
        )
        .toList();
  }
}