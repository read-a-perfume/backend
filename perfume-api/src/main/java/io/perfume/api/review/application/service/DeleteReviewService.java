package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.DeleteReviewPermissionDeniedException;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.in.DeleteReviewTagUseCase;
import io.perfume.api.review.application.in.DeleteReviewUseCase;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.application.out.ReviewRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteReviewService implements DeleteReviewUseCase {

  private final ReviewQueryRepository reviewQueryRepository;

  private final ReviewRepository reviewRepository;

  private final DeleteReviewTagUseCase deleteReviewTagUseCase;

  public DeleteReviewService(ReviewQueryRepository reviewQueryRepository,
                             ReviewRepository reviewRepository,
                             DeleteReviewTagUseCase deleteReviewTagUseCase
  ) {
    this.reviewQueryRepository = reviewQueryRepository;
    this.reviewRepository = reviewRepository;
    this.deleteReviewTagUseCase = deleteReviewTagUseCase;
  }

  @Override
  @Transactional
  public void delete(Long userId, Long reviewId, LocalDateTime now) {
    deleteReview(userId, reviewId, now);
    deleteReviewTagUseCase.deleteReviewTag(reviewId, now);
  }

  private void deleteReview(Long userId, Long reviewId, LocalDateTime now) {
    var review =
        reviewQueryRepository
            .findById(reviewId)
            .orElseThrow(() -> new NotFoundReviewException(reviewId));

    if (!review.isOwner(userId)) {
      throw new DeleteReviewPermissionDeniedException(userId, reviewId);
    }

    review.markDelete(now);
    reviewRepository.save(review);
  }
}
