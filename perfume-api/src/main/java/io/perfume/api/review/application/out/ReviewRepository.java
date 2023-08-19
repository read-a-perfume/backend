package io.perfume.api.review.application.out;

import io.perfume.api.review.domain.Review;

public interface ReviewRepository {

  Review save(Review review);
}
