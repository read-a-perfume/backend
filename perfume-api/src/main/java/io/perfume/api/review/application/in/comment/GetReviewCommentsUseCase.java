package io.perfume.api.review.application.in.comment;

import dto.repository.CursorPagination;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailCommand;
import io.perfume.api.review.application.facade.dto.ReviewCommentDetailResult;

public interface GetReviewCommentsUseCase {

  CursorPagination<ReviewCommentDetailResult> getReviewComments(
      final ReviewCommentDetailCommand command);
}
