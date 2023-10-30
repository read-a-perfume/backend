package io.perfume.api.review.application.in;

import java.time.LocalDateTime;

public interface DeleteReviewCommentUseCase {

  void delete(long id, long userId, LocalDateTime now);
}
