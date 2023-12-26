package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewCommentDetailCommand;

public record GetReviewCommentsRequestDto(Integer size, String before, String after) {

  public ReviewCommentDetailCommand toCommand(long reviewId) {
    return new ReviewCommentDetailCommand(reviewId, getSizeOrDefault(), after, before);
  }

  private Integer getSizeOrDefault() {
    return size == null ? 10 : size;
  }
}
