package io.perfume.api.review.adapter.out.persistence.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.review.adapter.out.persistence.mapper.ReviewMapper;
import io.perfume.api.review.adapter.out.persistence.mapper.TagMapper;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.Tag;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import java.time.LocalDateTime;
import java.util.List;
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

  @Autowired
  private ReviewPersistenceAdapter repository;

  @Test
  @DisplayName("리뷰를 저장한다.")
  void testSave() {
    // given
    var now = LocalDateTime.now();
    var tag = List.of(Tag.create("test", now));
    var review = Review.create(
        "test",
        "test description",
        STRENGTH.LIGHT,
        1000L,
        SEASON.DAILY,
        1L,
        1L,
        now
    );
    review.addTags(tag);

    // when
    var createdNote = repository.save(review);

    // then
    assertThat(createdNote.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdNote.getFeeling()).isEqualTo("test");
    assertThat(createdNote.getSituation()).isEqualTo("test description");
    assertThat(createdNote.getStrength()).isEqualTo(STRENGTH.LIGHT);
    assertThat(createdNote.getDuration()).isEqualTo(1000L);
    assertThat(createdNote.getSeason()).isEqualTo(SEASON.DAILY);
    assertThat(createdNote.getPerfumeId()).isEqualTo(1L);
    assertThat(createdNote.getUserId()).isEqualTo(1L);
    assertThat(createdNote.getCreatedAt()).isEqualTo(now);
    assertThat(createdNote.getUpdatedAt()).isEqualTo(now);
    assertThat(createdNote.getDeletedAt()).isNull();
    assertThat(createdNote.getTags().get(0).getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdNote.getTags().get(0).getName()).isEqualTo("test");
    assertThat(createdNote.getTags().get(0).getCreatedAt()).isEqualTo(now);
    assertThat(createdNote.getTags().get(0).getUpdatedAt()).isEqualTo(now);
    assertThat(createdNote.getTags().get(0).getDeletedAt()).isNull();
  }
}
