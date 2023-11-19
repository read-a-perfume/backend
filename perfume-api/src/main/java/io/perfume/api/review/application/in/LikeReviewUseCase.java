package io.perfume.api.review.application.in;

import java.time.LocalDateTime;

public interface LikeReviewUseCase {

  long toggleLikeReview(long userId, long reviewId, LocalDateTime now);
}
