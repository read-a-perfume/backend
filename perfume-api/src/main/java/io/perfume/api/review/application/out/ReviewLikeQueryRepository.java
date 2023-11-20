package io.perfume.api.review.application.out;

import io.perfume.api.review.domain.ReviewLike;
import java.util.Optional;

public interface ReviewLikeQueryRepository {

  Optional<ReviewLike> findByUserIdAndReviewId(long userId, long reviewId);

  long countByReviewId(long reviewId);
}
