package io.perfume.api.review.application.out;

import io.perfume.api.review.domain.ReviewLike;

public interface ReviewLikeRepository {

  ReviewLike save(ReviewLike review);
}
