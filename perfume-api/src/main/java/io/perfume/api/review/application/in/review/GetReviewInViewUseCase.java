package io.perfume.api.review.application.in.review;

import io.perfume.api.review.application.facade.dto.ReviewViewDetailResult;

public interface GetReviewInViewUseCase {

    ReviewViewDetailResult getReviewDetail(long reviewId);
}
