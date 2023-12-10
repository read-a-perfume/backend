package io.perfume.api.review.application.out.like;

import io.perfume.api.review.application.out.like.dto.ReviewLikeCount;
import io.perfume.api.review.domain.ReviewLike;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeQueryRepository {

  Optional<ReviewLike> findByUserIdAndReviewId(long userId, long reviewId);

  long countByReviewId(long reviewId);

  List<ReviewLikeCount> countByReviewIds(List<Long> reviewIds);
}
