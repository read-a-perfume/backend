package io.perfume.api.review.stub;

import io.perfume.api.review.application.out.ReviewLikeRepository;
import io.perfume.api.review.domain.ReviewLike;
import java.util.ArrayList;
import java.util.List;

public class StubReviewLikeRepository implements ReviewLikeRepository {

  private final List<ReviewLike> reviewLikes = new ArrayList<>();

  @Override
  public ReviewLike save(ReviewLike review) {
    reviewLikes.add(review);
    return review;
  }

  public void clear() {
    reviewLikes.clear();
  }
}
