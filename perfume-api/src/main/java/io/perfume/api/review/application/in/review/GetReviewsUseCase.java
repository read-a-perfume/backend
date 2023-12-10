package io.perfume.api.review.application.in.review;

import io.perfume.api.review.application.facade.dto.ReviewDetailResult;
import java.util.List;

public interface GetReviewsUseCase {

  List<ReviewDetailResult> getPaginatedReviews(long page, long size);
}
