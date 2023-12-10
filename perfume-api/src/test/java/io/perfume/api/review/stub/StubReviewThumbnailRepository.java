package io.perfume.api.review.stub;

import io.perfume.api.review.application.out.thumbnail.ReviewThumbnailRepository;
import io.perfume.api.review.domain.ReviewThumbnail;
import java.util.ArrayList;
import java.util.List;

public class StubReviewThumbnailRepository implements ReviewThumbnailRepository {

  private final List<ReviewThumbnail> reviewLikes = new ArrayList<>();

  @Override
  public ReviewThumbnail save(ReviewThumbnail thumbnail) {
    reviewLikes.add(thumbnail);
    return thumbnail;
  }

  @Override
  public List<ReviewThumbnail> saveAll(List<ReviewThumbnail> thumbnails) {
    reviewLikes.addAll(thumbnails);
    return thumbnails;
  }

  public void addReviewLike(ReviewThumbnail reviewLike) {
    reviewLikes.add(reviewLike);
  }

  public void clear() {
    reviewLikes.clear();
  }
}
