package io.perfume.api.review.adapter.out.persistence.repository.review;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.review.adapter.out.persistence.repository.tag.TagMapper;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({ReviewPersistenceAdapter.class, ReviewMapper.class, TagMapper.class})
@DataJpaTest
class ReviewPersistenceAdapterTest {

  @Autowired private ReviewPersistenceAdapter repository;

  @Test
  @DisplayName("리뷰를 저장한다.")
  void testSave() {
    // given
    var now = LocalDateTime.now();
    var review =
        Review.create(
            "test",
            "test description",
            Strength.LIGHT,
            Duration.TOO_SHORT,
            DayType.DAILY,
            1L,
            1L,
            Season.SPRING,
            now);

    // when
    var createdNote = repository.save(review);

    // then
    assertThat(createdNote.getId()).isNotNegative();
    assertThat(createdNote.getFullReview()).isEqualTo("test");
    assertThat(createdNote.getShortReview()).isEqualTo("test description");
    assertThat(createdNote.getStrength()).isEqualTo(Strength.LIGHT);
    assertThat(createdNote.getDuration()).isEqualTo(Duration.TOO_SHORT);
    assertThat(createdNote.getDayType()).isEqualTo(DayType.DAILY);
    assertThat(createdNote.getPerfumeId()).isEqualTo(1L);
    assertThat(createdNote.getUserId()).isEqualTo(1L);
    assertThat(createdNote.getDeletedAt()).isNull();
  }
}
