package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.application.facade.dto.ReviewCommentDetailResult;
import java.time.LocalDateTime;

public record GetReviewCommentsResponseDto(
    long id, Author author, String content, LocalDateTime createdAt) {

  public static GetReviewCommentsResponseDto from(
      final ReviewCommentDetailResult reviewCommentDetailResult) {
    return new GetReviewCommentsResponseDto(
        reviewCommentDetailResult.id(),
        new Author(
            reviewCommentDetailResult.user().id(),
            reviewCommentDetailResult.user().thumbnail(),
            reviewCommentDetailResult.user().name()),
        reviewCommentDetailResult.content(),
        reviewCommentDetailResult.createdAt());
  }

  public record Author(long id, String thumbnail, String name) {}
}
