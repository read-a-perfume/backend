package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import io.perfume.api.review.application.facade.dto.ReviewDetailUserResult;
import io.perfume.api.review.application.facade.dto.ReviewViewDetailResult;
import java.util.List;

public record GetReviewDetailResponseDto(
    Long id,
    String feeling,
    String situation,
    List<String> tags,
    List<String> images,
    ReviewDetailUserResult author,
    long likeCount,
    long commentCount
) {

  public static GetReviewDetailResponseDto from(ReviewViewDetailResult result) {
    return new GetReviewDetailResponseDto(
        result.id(),
        result.feeling(),
        result.situation(),
        result.tags(),
        result.images(),
        result.author(),
        result.likeCount(),
        result.commentCount()
    );
  }
}
