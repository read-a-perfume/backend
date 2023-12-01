package io.perfume.api.review.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.review.stub.StubReviewRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GetReviewCountServiceTest {

  private final StubReviewRepository stubReviewRepository = new StubReviewRepository();

  private final GetReviewCountService getReviewCountService =
      new GetReviewCountService(stubReviewRepository);

  @Test
  void getCountReview() {
    // given
    var now = LocalDateTime.now();
    var userId = 1L;
    var review = Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        userId,
        Season.SPRING,
        now
    );

    var review2 = Review.create(
        "test2",
        "test2 description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        userId,
        Season.SPRING,
        now
    );
    stubReviewRepository.save(review);
    stubReviewRepository.save(review2);

    // when
    var result = getReviewCountService.getReviewCountByUserId(userId);

    // then
    assertThat(result).isEqualTo(2L);
  }
}