package io.perfume.api.review.application.in;

import dto.repository.CursorPagination;
import io.perfume.api.review.application.in.dto.GetReviewCommentsCommand;
import io.perfume.api.review.domain.ReviewComment;

public interface GetReviewCommentsUseCase {

  CursorPagination<ReviewComment> getReviewComments(final GetReviewCommentsCommand command);
}
