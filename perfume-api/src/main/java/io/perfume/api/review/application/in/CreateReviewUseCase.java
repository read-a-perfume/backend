package io.perfume.api.review.application.in;

import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;

public interface CreateReviewUseCase {

  ReviewResult create(Long authorId, CreateReviewCommand command);
}
