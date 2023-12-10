package io.perfume.api.review.application.in.like;

import java.time.LocalDateTime;

public interface ReviewLikeUseCase {

  long toggleLikeReview(long userId, long reviewId, LocalDateTime now);
}
