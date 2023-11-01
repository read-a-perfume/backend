package io.perfume.api.review.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

  @Test
  @DisplayName("리뷰의 소유자인지 확인한다.")
  void testIsOwner() {
    // given
    var userId = 1L;
    var review = Review.create(
        "feeling",
        "shortReview",
        Strength.LIGHT,
        null,
        null,
        null,
        userId,
        Season.SPRING,
        null
    );

    // when
    var result = review.isOwner(userId);

    // then
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("리뷰의 소유자가 아닌지 확인한다.")
  void testIsNotOwner() {
    // given
    var userId = 1L;
    var review = Review.create(
        "feeling",
        "shortReview",
        Strength.LIGHT,
        null,
        null,
        null,
        userId,
        Season.SPRING,
        null
    );

    // when
    var result = review.isOwner(userId + 1);

    // then
    assertThat(result).isFalse();
  }
}
