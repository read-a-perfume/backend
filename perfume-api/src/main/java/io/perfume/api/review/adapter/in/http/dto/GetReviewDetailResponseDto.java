package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewDetailUserResult;
import io.perfume.api.review.application.facade.dto.ReviewViewDetailResult;
import java.util.List;

public record GetReviewDetailResponseDto(
    Long id,
    String shortReview,
    String fullReview,
    String dayType,
    String strength,
    String season,
    String duration,
    List<String> keywords,
    List<String> thumbnails,
    Long perfumeId,
    ReviewDetailUserResult author,
    long likeCount,
    long commentCount
) {

  public static GetReviewDetailResponseDto from(ReviewViewDetailResult result) {
    return new GetReviewDetailResponseDto(
        result.id(),
        result.shortReview(),
        result.fullReview(),
        result.dayType().getDescription(),
        result.strength().getDescription(),
        result.season().getDescription(),
        result.duration().getDescription(),
        result.tags(),
        result.images(),
        result.perfumeId(),
        result.author(),
        result.likeCount(),
        result.commentCount()
    );
  }
}
