package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewCommentDetailCommand;

public record GetReviewCommentsRequestDto(
    Long size,
    Long before,
    Long after
) {

  public ReviewCommentDetailCommand toCommand(long reviewId) {
    return new ReviewCommentDetailCommand(reviewId, getSizeOrDefault(), after, before);
  }

  private Long getSizeOrDefault() {
    return size == null ? 10 : size;
  }
}
