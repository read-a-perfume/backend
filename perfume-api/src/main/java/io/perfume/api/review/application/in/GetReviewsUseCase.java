package io.perfume.api.review.application.in;

import io.perfume.api.review.application.in.dto.ReviewResult;
import java.util.List;
import java.util.Optional;

public interface GetReviewsUseCase {

  List<ReviewResult> getPaginatedReviews(long page, long size);

  Optional<ReviewResult> getReview(long reviewId);

  long getLikeCount(long reviewId);

  long getCommentCount(long reviewId);
}
