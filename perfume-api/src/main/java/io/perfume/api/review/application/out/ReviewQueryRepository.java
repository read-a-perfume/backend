package io.perfume.api.review.application.out;

import io.perfume.api.review.domain.Review;
import java.util.Optional;

public interface ReviewQueryRepository {

  Optional<Review> findById(Long id);
}
