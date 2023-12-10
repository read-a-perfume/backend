package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewCommentDetailResult;
import java.time.LocalDateTime;

public record GetReviewCommentsResponseDto(
    long id, String authorName, String content, LocalDateTime createdAt) {

  public static GetReviewCommentsResponseDto from(
      ReviewCommentDetailResult reviewCommentDetailResult) {
    return new GetReviewCommentsResponseDto(
        reviewCommentDetailResult.id(),
        reviewCommentDetailResult.getAuthorName(),
        reviewCommentDetailResult.content(),
        reviewCommentDetailResult.createdAt());
  }
}
