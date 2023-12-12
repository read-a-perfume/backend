package io.perfume.api.review.application.facade.dto;

import io.perfume.api.review.application.in.dto.GetReviewCommentsCommand;

public record ReviewCommentDetailCommand(long reviewId, long size, Long after, Long before) {

  public GetReviewCommentsCommand toGetReviewCommentsCommand() {
    return new GetReviewCommentsCommand(size, before, after, reviewId);
  }
}
