package io.perfume.api.review.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.exception.NotPermittedLikeReviewException;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.review.stub.StubReviewLikeRepository;
import io.perfume.api.review.stub.StubReviewRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewServiceTest {

  private final StubReviewRepository reviewQueryRepository = new StubReviewRepository();

  private final StubReviewLikeRepository reviewLikeRepository = new StubReviewLikeRepository();

  private final ReviewService reviewService =
      new ReviewService(reviewQueryRepository, reviewLikeRepository);

  @BeforeEach
  void setUp() {
    reviewQueryRepository.clear();
    reviewLikeRepository.clear();
  }

  @Test
  @DisplayName("리뷰에 좋아요를 표시한다.")
  void testLikeReview() {
    // given
    final long userId = 1L;
    final long reviewId = 1L;
    final LocalDateTime now = LocalDateTime.now();

    // when
    final long expectedReviewId = reviewService.likeReview(userId, reviewId, now);

    // then
    assertThat(reviewId).isEqualTo(expectedReviewId);
  }

  @Test
  @DisplayName("리뷰가 존재하지 않을 경우 좋아요 요청이 실패한다.")
  void testLikeReviewFailIfReviewNotExist() {
    // given
    final long userId = 1L;
    final long reviewId = 1L;
    final LocalDateTime now = LocalDateTime.now();

    // when & then
    assertThatThrownBy(() -> reviewService.likeReview(userId, reviewId, now))
        .isInstanceOf(NotFoundReviewException.class);
  }

  @Test
  @DisplayName("자신의 리뷰에 좋아료를 표시할 수 없다.")
  void testLikeReviewFailIfReviewIsMine() {
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
            0L,
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
    assertThatThrownBy(() -> reviewService.likeReview(userId, reviewId, now))
        .isInstanceOf(NotPermittedLikeReviewException.class);
  }
}
