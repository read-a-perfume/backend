package io.perfume.api.review.application.in.review;

import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import io.perfume.api.review.application.in.dto.ReviewResult;
import java.util.List;
import java.util.Optional;

public interface GetReviewsUseCase {

  List<ReviewDetailResult> getPaginatedReviews(long page, long size);
}
