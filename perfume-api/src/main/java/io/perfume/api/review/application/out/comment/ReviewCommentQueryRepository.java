package io.perfume.api.review.application.out.comment;

import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.review.application.out.comment.dto.ReviewCommentCount;
import io.perfume.api.review.domain.ReviewComment;
import java.util.List;
import java.util.Optional;

public interface ReviewCommentQueryRepository {

  Optional<ReviewComment> findById(Long id);

  CursorPagination<ReviewComment> findByReviewId(
      final CursorPageable<Long> pageable, final long reviewId);

  long countByReviewId(final long reviewId);

  List<ReviewCommentCount> countByReviewIds(List<Long> reviewIds);
}
