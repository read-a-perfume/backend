package io.perfume.api.review.application.facade.dto;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.review.domain.ReviewComment;
import io.perfume.api.user.application.port.in.dto.UserResult;
import java.time.LocalDateTime;

public record ReviewCommentDetailResult(
    Long id, ReviewCommentUserResult user, String content, LocalDateTime createdAt) {

  public static ReviewCommentDetailResult from(
      final ReviewComment comment, final UserResult user, final FileResult thumbnail) {
    final var reviewCommentUserResult = ReviewCommentUserResult.from(user, thumbnail);

    return new ReviewCommentDetailResult(
        comment.getId(), reviewCommentUserResult, comment.getContent(), comment.getCreatedAt());
  }

  public String getAuthorName() {
    return user.name();
  }
}
