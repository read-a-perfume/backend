package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.NotFoundReviewCommentException;
import io.perfume.api.review.application.in.DeleteReviewCommentUseCase;
import io.perfume.api.review.application.out.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.ReviewCommentRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class DeleteReviewCommentService implements DeleteReviewCommentUseCase {

  private final ReviewCommentRepository reviewCommentRepository;

  private final ReviewCommentQueryRepository reviewCommentQueryRepository;

  public DeleteReviewCommentService(
      ReviewCommentRepository reviewCommentRepository,
      ReviewCommentQueryRepository reviewCommentQueryRepository) {
    this.reviewCommentRepository = reviewCommentRepository;
    this.reviewCommentQueryRepository = reviewCommentQueryRepository;
  }

  @Override
  public void delete(final long id, final long userId, final LocalDateTime now) {
    final var reviewComment =
        reviewCommentQueryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundReviewCommentException(id));
    reviewComment.markDelete(userId, now);
    reviewCommentRepository.save(reviewComment);
  }
}
