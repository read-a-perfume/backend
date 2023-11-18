package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.exception.NotPermittedLikeReviewException;
import io.perfume.api.review.application.out.ReviewLikeRepository;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewLike;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {

  private final ReviewQueryRepository reviewQueryRepository;

  private final ReviewLikeRepository reviewLikeRepository;

  public ReviewService(final ReviewQueryRepository reviewQueryRepository, final ReviewLikeRepository reviewLikeRepository) {
    this.reviewQueryRepository = reviewQueryRepository;
    this.reviewLikeRepository = reviewLikeRepository;
  }

  public long likeReview(long userId, long reviewId, LocalDateTime now) {
    final Review review = reviewQueryRepository.findById(reviewId)
        .orElseThrow(() -> new NotFoundReviewException(reviewId));

    if (review.isMine(userId)) {
        throw new NotPermittedLikeReviewException(userId, reviewId);
    }

    final ReviewLike reviewLike = ReviewLike.create(userId, reviewId, now);
    reviewLikeRepository.save(reviewLike);

    return reviewId;
  }
}
