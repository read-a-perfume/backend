package io.perfume.api.review.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.exception.NotPermittedLikeReviewException;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewLike;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.review.stub.StubReviewLikeRepository;
import io.perfume.api.review.stub.StubReviewRepository;
import io.perfume.api.review.stub.StubReviewThumbnailRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
class ReviewServiceTest {

  private final StubReviewRepository reviewQueryRepository = new StubReviewRepository();
  private final StubReviewLikeRepository reviewLikeRepository = new StubReviewLikeRepository();
  private final StubReviewThumbnailRepository reviewThumbnailRepository =
      new StubReviewThumbnailRepository();

  private final ReviewService reviewService = new ReviewService(
      reviewQueryRepository,
      reviewQueryRepository,
      reviewLikeRepository,
      reviewLikeRepository,
      reviewThumbnailRepository);

  @BeforeEach
  void setUp() {
    reviewQueryRepository.clear();
    reviewLikeRepository.clear();
    reviewThumbnailRepository.clear();
  }

  @Test
  void 리뷰_좋아요_표시() {
    // given
    final long userId = 1L;
    final long reviewId = 1L;
    final LocalDateTime now = LocalDateTime.now();
    reviewQueryRepository.addReview(
        new Review(
            reviewId,
            "",
            "",
            Strength.LIGHT,
            Duration.TOO_SHORT,
            DayType.DAILY,
            1L,
            2L,
            Season.SPRING,
            now,
            now,
            null
        )
    );

    // when
    final long expectedReviewId = reviewService.toggleLikeReview(userId, reviewId, now);

    // then
    assertThat(reviewId).isEqualTo(expectedReviewId);
  }

  @Test
  void 없는_리뷰_좋아요_요청_실패() {
    // given
    final long userId = 1L;
    final long reviewId = 1L;
    final LocalDateTime now = LocalDateTime.now();

    // when & then
    assertThatThrownBy(() -> reviewService.toggleLikeReview(userId, reviewId, now))
        .isInstanceOf(NotFoundReviewException.class);
  }

  @Test
  void 본인_리뷰_좋아요_차단() {
    // given
    final long userId = 1L;
    final long reviewId = 1L;
    final LocalDateTime now = LocalDateTime.now();
    reviewQueryRepository.addReview(
        new Review(
            reviewId,
            "",
            "",
            Strength.LIGHT,
            Duration.TOO_SHORT,
            DayType.DAILY,
            1L,
            1L,
            Season.SPRING,
            now,
            now,
            null
        )
    );

    // when & then
    assertThatThrownBy(() -> reviewService.toggleLikeReview(userId, reviewId, now))
        .isInstanceOf(NotPermittedLikeReviewException.class);
  }

  @Test
  void 이미_좋아요한_경우_취소() {
    // given
    final long userId = 1L;
    final long reviewId = 1L;
    final LocalDateTime now = LocalDateTime.now();
    reviewQueryRepository.addReview(
        new Review(
            reviewId,
            "",
            "",
            Strength.LIGHT,
            Duration.TOO_SHORT,
            DayType.DAILY,
            1L,
            2L,
            Season.SPRING,
            now,
            now,
            null
        )
    );
    ReviewLike expectReviewLike = new ReviewLike(
        1L,
        userId,
        reviewId,
        now,
        now,
        null
    );
    reviewLikeRepository.addReviewLike(
        expectReviewLike
    );

    // when
    reviewService.toggleLikeReview(userId, reviewId, now);

    // then
    assertThat(expectReviewLike.getDeletedAt()).isEqualTo(now);
  }
}
