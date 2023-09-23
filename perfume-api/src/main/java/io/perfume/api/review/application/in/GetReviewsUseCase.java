package io.perfume.api.review.application.in;

import io.perfume.api.review.application.in.dto.ReviewResult;
import java.util.List;

public interface GetReviewsUseCase {

  List<ReviewResult> getPaginatedReviews(long page, long size);
}
