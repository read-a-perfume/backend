package io.perfume.api.review.stub;

import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubReviewRepository implements ReviewRepository, ReviewQueryRepository {

  private final List<Review> reviews = new ArrayList<>();

  @Override
  public Optional<Review> findById(Long id) {
    return reviews.stream()
        .filter(review -> review.getId().equals(id))
        .findFirst();
  }

  @Override
  public Review save(Review review) {
    reviews.add(review);
    return review;
  }

  @Override
  public List<Review> findByPage(long page, long size) {
    return null;
  }

  @Override
  public ReviewFeatureCount getReviewFeatureCount(Long perfumeId) {
    return null;
  }

  public Review addReview(Review review) {
    reviews.add(review);
    return review;
  }

  public void clear() {
    reviews.clear();
  }
}
