package io.perfume.api.review.application.facade.dto;

import io.perfume.api.review.application.in.dto.GetReviewCommentsCommand;

public record ReviewCommentDetailCommand(long reviewId, int size, String after, String before) {

  public GetReviewCommentsCommand toGetReviewCommentsCommand() {
    return new GetReviewCommentsCommand(size, before, after, reviewId);
  }
}
