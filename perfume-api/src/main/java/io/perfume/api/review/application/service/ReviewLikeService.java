package io.perfume.api.review.application.service;

import io.perfume.api.common.notify.EventPublisher;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.exception.NotPermittedLikeReviewException;
import io.perfume.api.review.application.facade.dto.ReviewLikeEvent;
import io.perfume.api.review.application.out.like.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.like.ReviewLikeRepository;
import io.perfume.api.review.application.out.like.dto.ReviewLikeCount;
import io.perfume.api.review.application.out.review.ReviewQueryRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewLike;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewLikeService {

  private final ReviewLikeQueryRepository reviewLikeQueryRepository;
  private final ReviewLikeRepository reviewLikeRepository;
  private final ReviewQueryRepository reviewQueryRepository;
  private final EventPublisher eventPublisher;

  @Transactional
  public long toggleLikeReview(long userId, long reviewId, LocalDateTime now) {
    Review review = findReviewById(reviewId);
    verifyReviewOwnership(userId, review);

    Optional<ReviewLike> reviewLike =
        reviewLikeQueryRepository.findByUserIdAndReviewId(userId, reviewId);
    reviewLike.ifPresentOrElse(
        like -> deleteReviewLike(like, now),
        () -> addReviewLike(userId, reviewId, review.getUserId(), now));

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

  private void addReviewLike(long userId, long reviewId, long receiveUserId, LocalDateTime now) {
    ReviewLike newReviewLike = ReviewLike.create(userId, reviewId, now);
    ReviewLikeEvent reviewLikeEvent = new ReviewLikeEvent(receiveUserId);
    eventPublisher.publishEvent(reviewLikeEvent);
    reviewLikeRepository.save(newReviewLike);
  }

  public List<ReviewLikeCount> getReviewLikeCount(List<Long> reviewIds) {
    return reviewLikeQueryRepository.countByReviewIds(reviewIds);
  }
}
