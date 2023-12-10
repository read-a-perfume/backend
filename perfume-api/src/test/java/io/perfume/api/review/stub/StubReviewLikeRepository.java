package io.perfume.api.review.stub;

import io.perfume.api.review.application.out.like.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.like.ReviewLikeRepository;
import io.perfume.api.review.application.out.like.dto.ReviewLikeCount;
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

  @Override
  public long countByReviewId(long reviewId) {
    return 0;
  }

  public void addReviewLike(ReviewLike reviewLike) {
    reviewLikes.add(reviewLike);
  }

  @Override
  public List<ReviewLikeCount> countByReviewIds(List<Long> reviewIds) {
    return reviewLikes.stream()
        .map(reviewLike -> new ReviewLikeCount(reviewLike.getReviewId(), 1))
        .toList();
  }

  public void clear() {
    reviewLikes.clear();
  }
}
