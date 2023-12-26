package io.perfume.api.review.application.service;

import io.perfume.api.common.page.CustomPage;
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
import org.springframework.data.domain.Pageable;
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
    final ReviewFeatureCount reviewFeatureCount =
        reviewQueryRepository.getReviewFeatureCount(perfumeId);

    final long totalReviewCount = reviewFeatureCount.totalReviews();
    return new ReviewStatisticResult(
        calculateUserReviewStatisticsPercentage(
            Strength.class, reviewFeatureCount.strengthMap(), totalReviewCount),
        calculateUserReviewStatisticsPercentage(
            Duration.class, reviewFeatureCount.durationMap(), totalReviewCount),
        calculateUserReviewStatisticsPercentage(
            Season.class, reviewFeatureCount.seasonMap(), totalReviewCount),
        calculateUserReviewStatisticsPercentage(
            DayType.class, reviewFeatureCount.dayTypeMap(), totalReviewCount),
        calculateUserReviewStatisticsPercentage(
            Sex.class, reviewFeatureCount.sexMap(), totalReviewCount));
  }

  private <V extends Enum<V>> Map<V, Long> calculateUserReviewStatisticsPercentage(
      final Class<V> enumClazz, final Map<V, Long> map, final long totalReviews) {
    Map<V, Long> mutableMap = new HashMap<>(map);
    EnumSet.allOf(enumClazz).forEach(enumValue -> mutableMap.putIfAbsent(enumValue, 0L));
    return mutableMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, e -> Math.round((double) e.getValue() / totalReviews * 100)));
  }

  public CustomPage<ReviewResult> getReviewsByPerfumeId(long perfumeId, Pageable pageable) {
    final CustomPage<Review> reviews = reviewQueryRepository.findByPerfumeId(perfumeId, pageable);
    final List<ReviewResult> reviewResults =
        reviews.getContent().stream().map(ReviewResult::from).toList();

    return new CustomPage<>(reviewResults, reviews);
  }
}
