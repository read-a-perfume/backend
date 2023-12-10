package io.perfume.api.review.application.out.comment;

import io.perfume.api.review.domain.ReviewComment;

public interface ReviewCommentRepository {

  ReviewComment save(ReviewComment domain);
}
