package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.in.dto.CreateReviewCommentCommand;

public record CreateReviewCommentRequestDto(String content) {

  public CreateReviewCommentCommand toCommand(Long userId, Long reviewId) {
    return new CreateReviewCommentCommand(userId, reviewId, content);
  }
}
