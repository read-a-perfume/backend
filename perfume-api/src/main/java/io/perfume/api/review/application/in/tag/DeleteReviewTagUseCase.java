package io.perfume.api.review.application.in.tag;

import java.time.LocalDateTime;

public interface DeleteReviewTagUseCase {

  void deleteReviewTag(Long reviewId, LocalDateTime now);
}
