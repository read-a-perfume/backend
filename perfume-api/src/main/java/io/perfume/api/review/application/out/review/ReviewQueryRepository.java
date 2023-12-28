package io.perfume.api.review.application.out.review;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository {

  Optional<Review> findById(Long id);

  CustomPage<Review> findByPage(Pageable pageable);

  ReviewFeatureCount getReviewFeatureCount(long perfumeId);

  Long findReviewCountByUserId(Long userId);

  boolean existsReviewById(Long reviewId);

  CustomPage<Review> findByPerfumeId(long perfumeId, Pageable pageable);
}
