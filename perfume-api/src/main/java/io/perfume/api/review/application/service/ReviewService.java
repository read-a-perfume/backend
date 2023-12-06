package io.perfume.api.review.application.service;

import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.exception.NotPermittedLikeReviewException;
import io.perfume.api.review.application.in.AddReviewThumbnailUseCase;
import io.perfume.api.review.application.in.CreateReviewUseCase;
import io.perfume.api.review.application.in.LikeReviewUseCase;
import io.perfume.api.review.application.in.dto.CreateReviewCommand;
import io.perfume.api.review.application.in.dto.ReviewResult;
import io.perfume.api.review.application.out.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.ReviewLikeRepository;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.application.out.ReviewThumbnailRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewLike;
import io.perfume.api.review.domain.ReviewThumbnail;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements LikeReviewUseCase, AddReviewThumbnailUseCase,
    CreateReviewUseCase {

  private final ReviewRepository reviewRepository;
  private final ReviewQueryRepository reviewQueryRepository;
  private final ReviewLikeRepository reviewLikeRepository;
  private final ReviewLikeQueryRepository reviewLikeQueryRepository;
  private final ReviewThumbnailRepository reviewThumbnailRepository;

  public long toggleLikeReview(long userId, long reviewId, LocalDateTime now) {
    Review review = findReviewById(reviewId);
    verifyReviewOwnership(userId, review);

    Optional<ReviewLike> reviewLike =
        reviewLikeQueryRepository.findByUserIdAndReviewId(userId, reviewId);
    reviewLike.ifPresentOrElse(like -> deleteReviewLike(like, now),
        () -> addReviewLike(userId, reviewId, now));

    return reviewId;
  }

  @Override
  @Transactional
  public ReviewResult create(Long authorId, CreateReviewCommand command) {
    Review createdReview = reviewRepository.save(createReview(authorId, command));
    addThumbnails(createdReview.getId(), command.thumbnailIds(), command.now());

    return ReviewResult.from(createdReview);
  }

  @Override
  public List<ReviewThumbnail> addThumbnails(final Long reviewId, final List<Long> thumbnailIds,
                                             final LocalDateTime now) {
    if (thumbnailIds.isEmpty()) {
      return List.of();
    }

    return reviewThumbnailRepository.saveAll(
        thumbnailIds.stream()
            .map(thumbnailId -> new ReviewThumbnail(reviewId, thumbnailId, now, now, null))
            .toList()
    );
  }

  private Review findReviewById(long reviewId) {
    return reviewQueryRepository.findById(reviewId)
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

  @NotNull
  private static Review createReview(Long authorId, CreateReviewCommand command) {
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
}
