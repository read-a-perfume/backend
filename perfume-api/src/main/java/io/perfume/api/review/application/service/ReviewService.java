package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.DeleteReviewPermissionDeniedException;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.application.out.comment.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.like.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.review.ReviewQueryRepository;
import io.perfume.api.review.application.out.review.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService {

  private final ReviewQueryRepository reviewQueryRepository;
  private final ReviewLikeQueryRepository reviewLikeQueryRepository;
  private final ReviewCommentQueryRepository reviewCommentQueryRepository;
  private final ReviewRepository reviewRepository;

  public ReviewResult create(Long authorId, CreateReviewCommand command) {
    Review createdReview = reviewRepository.save(createReview(authorId, command));
    return ReviewResult.from(createdReview);
  }

  private Review createReview(Long authorId, CreateReviewCommand command) {
    return Review.create(
        command.shortReview(),
        command.situation(),
        command.strength(),
        command.duration(),
        command.dayType(),
        command.perfumeId(),
        authorId,
        command.season(),
        command.now());
  }

  public void delete(Long userId, Long reviewId, LocalDateTime now) {
    deleteReview(userId, reviewId, now);
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

  @Transactional(readOnly = true)
  public Long getReviewCountByUserId(Long userId) {
    return reviewQueryRepository.findReviewCountByUserId(userId);
  }

  private <V extends Enum<V>> Map<V, Long> createEmptyMap(Class<V> enumClass) {
    Map<V, Long> map = new HashMap<>();
    EnumSet.allOf(enumClass).forEach(enumValue -> map.putIfAbsent(enumValue, 0L));
    return map;
  }

  @Transactional(readOnly = true)
  public List<ReviewResult> getPaginatedReviews(long page, long size) {
    return reviewQueryRepository.findByPage(page, size).stream().map(ReviewResult::from).toList();
  }

  @Transactional(readOnly = true)
  public Optional<ReviewResult> getReview(long reviewId) {
    return reviewQueryRepository.findById(reviewId).map(ReviewResult::from);
  }

  @Transactional(readOnly = true)
  public long getLikeCount(long reviewId) {
    return reviewLikeQueryRepository.countByReviewId(reviewId);
  }

  @Transactional(readOnly = true)
  public long getCommentCount(long reviewId) {
    return reviewCommentQueryRepository.countByReviewId(reviewId);
  }

  @Transactional(readOnly = true)
  public ReviewStatisticResult getStatisticByPerfume(Long perfumeId) {
    ReviewFeatureCount reviewFeatureCount = reviewQueryRepository.getReviewFeatureCount(perfumeId);

    Long totalReviews = reviewFeatureCount.totalReviews();
    if (totalReviews == 0) {
      return new ReviewStatisticResult(
          createEmptyMap(Strength.class),
          createEmptyMap(Duration.class),
          createEmptyMap(Season.class),
          createEmptyMap(DayType.class),
          createEmptyMap(Sex.class));
    }
    return new ReviewStatisticResult(
        calculatePercentage(reviewFeatureCount.strengthMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.durationMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.seasonMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.dayTypeMap(), totalReviews),
        calculatePercentage(reviewFeatureCount.sexMap(), totalReviews));
  }

  private <V extends Enum<V>> Map<V, Long> calculatePercentage(
      Map<V, Long> map, Long totalReviews) {
    EnumSet.allOf(map.keySet().iterator().next().getDeclaringClass())
        .forEach(enumValue -> map.putIfAbsent(enumValue, 0L));

    return map.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, e -> Math.round((double) e.getValue() / totalReviews * 100)));
  }
}
