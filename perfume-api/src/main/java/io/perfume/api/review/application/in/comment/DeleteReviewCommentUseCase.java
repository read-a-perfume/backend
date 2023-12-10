package io.perfume.api.review.application.in.comment;

import java.time.LocalDateTime;

public interface DeleteReviewCommentUseCase {

  void deleteComment(long id, long userId, LocalDateTime now);
}
