package io.perfume.api.review.application.out.comment;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.review.domain.ReviewComment;
import java.util.Optional;

public interface ReviewCommentQueryRepository {

  Optional<ReviewComment> findById(Long id);

  CursorPagination<ReviewComment> findByReviewId(final CursorPageable<Long> pageable, final long reviewId);

  long countByReviewId(final long reviewId);
}
