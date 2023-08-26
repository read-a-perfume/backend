package io.perfume.api.review.application.in;

import java.time.LocalDateTime;

public interface DeleteReviewTagUseCase {

  void deleteReviewTag(Long reviewId, LocalDateTime now);
}
