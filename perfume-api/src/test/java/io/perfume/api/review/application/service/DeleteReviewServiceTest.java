package io.perfume.api.review.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.perfume.api.review.application.exception.DeleteReviewPermissionDeniedException;
import io.perfume.api.review.application.exception.NotFoundReviewException;
import io.perfume.api.review.application.in.DeleteReviewTagUseCase;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import io.perfume.api.review.stub.StubReviewRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteReviewServiceTest {

  private final StubReviewRepository stubReviewRepository = new StubReviewRepository();

  private final DeleteReviewTagUseCase mockDeleteReviewTagUseCase =
      Mockito.mock(DeleteReviewTagUseCase.class);

  private final DeleteReviewService deleteReviewService =
      new DeleteReviewService(stubReviewRepository, stubReviewRepository,
          mockDeleteReviewTagUseCase);

  @Test
  @DisplayName("리뷰를 삭제한다.")
  void testDelete() {
    // given
    var userId = 1L;
    var reviewId = 1L;
    var now = LocalDateTime.now();
    var review =
        new Review(reviewId, "", "", STRENGTH.LIGHT, 0L, SEASON.DAILY, 1L, userId, now, now, null);
    stubReviewRepository.addReview(review);

    // when
    deleteReviewService.delete(userId, reviewId, now);

    // then
    assertThat(review.getDeletedAt()).isNotNull();
    Mockito
        .verify(mockDeleteReviewTagUseCase, Mockito.times(1))
        .deleteReviewTag(reviewId, now);
  }

  @Test
  @DisplayName("내 리뷰가 아닌 경우 삭제에 실패한다.")
  void testNotPermissionDeleteReview() {
    // given
    var userId = 1L;
    var reviewId = 1L;
    var now = LocalDateTime.now();
    var review =
        new Review(reviewId, "", "", STRENGTH.LIGHT, 0L, SEASON.DAILY, 1L, userId, now, now, null);
    stubReviewRepository.addReview(review);

    // when & then
    assertThrows(
        DeleteReviewPermissionDeniedException.class,
        () ->
            deleteReviewService.delete(userId + 1, reviewId, now));
    Mockito
        .verify(mockDeleteReviewTagUseCase, Mockito.times(0))
        .deleteReviewTag(reviewId, now);
  }

  @Test
  @DisplayName("삭제할 리뷰가 존재하지 않는 경우 실패한다.")
  void testNotFoundReview() {
    // given
    var userId = 1L;
    var reviewId = 1L;
    var now = LocalDateTime.now();

    // when & then
    assertThrows(
        NotFoundReviewException.class,
        () ->
            deleteReviewService.delete(userId, reviewId, now));
    Mockito
        .verify(mockDeleteReviewTagUseCase, Mockito.times(0))
        .deleteReviewTag(reviewId, now);
  }
}
