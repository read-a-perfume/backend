package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.exception.NotPermittedLikeReviewException;
import io.perfume.api.review.application.in.LikeReviewUseCase;
import io.perfume.api.review.application.out.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.ReviewLikeRepository;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewLike;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements LikeReviewUseCase {

  private final ReviewQueryRepository reviewQueryRepository;
  private final ReviewLikeRepository reviewLikeRepository;
  private final ReviewLikeQueryRepository reviewLikeQueryRepository;

  public long toggleLikeReview(long userId, long reviewId, LocalDateTime now) {
    Review review = findReviewById(reviewId);
    verifyReviewOwnership(userId, review);

    Optional<ReviewLike> reviewLike =
        reviewLikeQueryRepository.findByUserIdAndReviewId(userId, reviewId);
    reviewLike.ifPresentOrElse(
        like -> deleteReviewLike(like, now), () -> addReviewLike(userId, reviewId, now));

    return reviewId;
  }

  private Review findReviewById(long reviewId) {
    return reviewQueryRepository
        .findById(reviewId)
        .orElseThrow(() -> new NotFoundReviewException(reviewId));
  }

  private void verifyReviewOwnership(long userId, Review review) {
    if (review.isMine(userId)) {
      throw new NotPermittedLikeReviewException(userId, review.getId());
    }
  }

  private void deleteReviewLike(ReviewLike like, LocalDateTime now) {
    like.markDelete(now);
    reviewLikeRepository.save(like);
  }

  private void addReviewLike(long userId, long reviewId, LocalDateTime now) {
    ReviewLike newReviewLike = ReviewLike.create(userId, reviewId, now);
    reviewLikeRepository.save(newReviewLike);
  }
}
