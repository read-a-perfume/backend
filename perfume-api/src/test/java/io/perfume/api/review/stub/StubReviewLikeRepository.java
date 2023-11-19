package io.perfume.api.review.stub;

import io.perfume.api.review.application.out.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.ReviewLikeRepository;
import io.perfume.api.review.domain.ReviewLike;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubReviewLikeRepository implements ReviewLikeRepository, ReviewLikeQueryRepository {

  private final List<ReviewLike> reviewLikes = new ArrayList<>();

  @Override
  public ReviewLike save(ReviewLike review) {
    reviewLikes.add(review);
    return review;
  }

  @Override
  public Optional<ReviewLike> findByUserIdAndReviewId(long userId, long reviewId) {
    if (reviewLikes.isEmpty()) {
      return Optional.empty();
    }
    return Optional.ofNullable(reviewLikes.get(0));
  }

  public void addReviewLike(ReviewLike reviewLike) {
    reviewLikes.add(reviewLike);
  }

  public void clear() {
    reviewLikes.clear();
  }
}
