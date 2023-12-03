package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.NotFoundReviewCommentException;
import io.perfume.api.review.application.exception.UpdateReviewCommentPermissionDeniedException;
import io.perfume.api.review.application.in.UpdateReviewCommentUseCase;
import io.perfume.api.review.application.out.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.ReviewCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateReviewCommentService implements UpdateReviewCommentUseCase {

  private final ReviewCommentRepository reviewCommentRepository;

  private final ReviewCommentQueryRepository reviewQueryRepository;

  public UpdateReviewCommentService(
      ReviewCommentRepository reviewCommentRepository,
      ReviewCommentQueryRepository reviewQueryRepository) {
    this.reviewCommentRepository = reviewCommentRepository;
    this.reviewQueryRepository = reviewQueryRepository;
  }

  @Override
  public void updateReviewComment(Long userId, Long commentId, String newComment) {
    final var comment =
        reviewQueryRepository
            .findById(commentId)
            .orElseThrow(() -> new NotFoundReviewCommentException(commentId));

    if (!comment.isOwned(userId)) {
      throw new UpdateReviewCommentPermissionDeniedException(userId, commentId);
    }

    comment.updateComment(newComment);
    reviewCommentRepository.save(comment);
  }
}
