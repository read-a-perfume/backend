package io.perfume.api.review.application.out.review;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository {

  Optional<Review> findById(Long id);

  List<Review> findByPage(long page, long size);

  ReviewFeatureCount getReviewFeatureCount(Long perfumeId);

  Long findReviewCountByUserId(Long userId);

  boolean existsReviewById(Long reviewId);

  CustomPage<Review> findByPerfumeId(final long perfumeId, final Pageable pageable);
}
