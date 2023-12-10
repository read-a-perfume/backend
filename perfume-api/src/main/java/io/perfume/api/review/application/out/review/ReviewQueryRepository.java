package io.perfume.api.review.application.out.review;

import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import java.util.List;
import java.util.Optional;

public interface ReviewQueryRepository {

  Optional<Review> findById(Long id);

  List<Review> findByPage(long page, long size);

  ReviewFeatureCount getReviewFeatureCount(Long perfumeId);

  Long findReviewCountByUserId(Long userId);

  boolean existsReviewById(Long reviewId);
}
