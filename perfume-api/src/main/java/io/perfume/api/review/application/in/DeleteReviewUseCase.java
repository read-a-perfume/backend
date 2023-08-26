package io.perfume.api.review.application.in;

import java.time.LocalDateTime;

public interface DeleteReviewUseCase {

  void delete(Long userId, Long reviewId, LocalDateTime now);
}
