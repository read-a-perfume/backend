package io.perfume.api.review.application.in;

public interface UpdateReviewCommentUseCase {

  void updateReviewComment(Long userId, Long commentId, String newComment);
}
