package io.perfume.api.review.application.in.review;

import java.time.LocalDateTime;

public interface DeleteReviewUseCase {

  void delete(Long userId, Long reviewId, LocalDateTime now);
}
