package io.perfume.api.review.application.in.review;

import io.perfume.api.common.page.CustomPage;
import io.perfume.api.review.application.facade.dto.ReviewDetailResult;

public interface GetUserReviewsUseCase {

  CustomPage<ReviewDetailResult> getUserReviews(long userId, int page, int size);
}
