package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.DeleteReviewPermissionDeniedException;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.application.in.review.*;
import io.perfume.api.review.application.out.comment.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.like.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.review.ReviewQueryRepository;
import io.perfume.api.review.application.out.review.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewFeatureCount;
import io.perfume.api.review.domain.ReviewThumbnail;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
                command.now()
        );
    }

    @Transactional
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

    public Long getReviewCountByUserId(Long userId) {
        return reviewQueryRepository.findReviewCountByUserId(userId);
    }

    private <V extends Enum<V>> Map<V, Long> createEmptyMap(Class<V> enumClass) {
        Map<V, Long> map = new HashMap<>();
        EnumSet.allOf(enumClass).forEach(
                enumValue -> map.putIfAbsent(enumValue, 0L)
        );
        return map;
    }

    public List<ReviewResult> getPaginatedReviews(long page, long size) {
        return reviewQueryRepository
                .findByPage(page, size)
                .stream()
                .map(ReviewResult::from)
                .toList();
    }

    public Optional<ReviewResult> getReview(long reviewId) {
        return reviewQueryRepository.findById(reviewId).map(ReviewResult::from);
    }

    public long getLikeCount(long reviewId) {
        return reviewLikeQueryRepository.countByReviewId(reviewId);
    }

    public long getCommentCount(long reviewId) {
        return reviewCommentQueryRepository.countByReviewId(reviewId);
    }

    public ReviewStatisticResult getStatisticByPerfume(Long perfumeId) {
        ReviewFeatureCount reviewFeatureCount = reviewQueryRepository.getReviewFeatureCount(perfumeId);

        Long totalReviews = reviewFeatureCount.totalReviews();
        if (totalReviews == 0) {
            return new ReviewStatisticResult(
                    createEmptyMap(Strength.class),
                    createEmptyMap(Duration.class),
                    createEmptyMap(Season.class),
                    createEmptyMap(DayType.class),
                    createEmptyMap(Sex.class)
            );
        }
        return new ReviewStatisticResult(
                calculatePercentage(reviewFeatureCount.strengthMap(), totalReviews),
                calculatePercentage(reviewFeatureCount.durationMap(), totalReviews),
                calculatePercentage(reviewFeatureCount.seasonMap(), totalReviews),
                calculatePercentage(reviewFeatureCount.dayTypeMap(), totalReviews),
                calculatePercentage(reviewFeatureCount.sexMap(), totalReviews)
        );
    }

    private <V extends Enum<V>> Map<V, Long> calculatePercentage(Map<V, Long> map, Long totalReviews) {
        EnumSet.allOf(map.keySet().iterator().next().getDeclaringClass()).forEach(
                enumValue -> map.putIfAbsent(enumValue, 0L)
        );

        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Math.round((double) e.getValue() / totalReviews * 100)));
    }
}
