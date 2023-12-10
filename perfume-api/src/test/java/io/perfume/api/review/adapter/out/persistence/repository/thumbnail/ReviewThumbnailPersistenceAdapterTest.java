package io.perfume.api.review.adapter.out.persistence.repository.thumbnail;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.fixture.ReviewThumbnailFixture;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({
  ReviewThumbnailPersistenceAdapter.class,
  ReviewThumbnailMapper.class,
  ReviewThumbnailFixture.class,
  TestQueryDSLConfiguration.class
})
@DataJpaTest
@EnableJpaAuditing
@SuppressWarnings("NonAsciiCharacters")
class ReviewThumbnailPersistenceAdapterTest {

  @Autowired private ReviewThumbnailPersistenceAdapter repository;

  @Autowired private ReviewThumbnailFixture fixture;

  @Test
  void 리뷰_썸네일_저장() {
    // given
    var now = LocalDateTime.now();
    var entity = fixture.createReviewThumbnail(1, 1, now);

    // when
    var result = repository.save(entity);

    // then
    assertThat(result.getReviewId()).isEqualTo(1L);
    assertThat(result.getThumbnailId()).isEqualTo(1L);
  }

  @Test
  void 리뷰_썸네일_복수_저장() {
    // given
    var now = LocalDateTime.now();
    var entities =
        List.of(fixture.createReviewThumbnail(1, 1, now), fixture.createReviewThumbnail(1, 2, now));

    // when
    var result = repository.saveAll(entities);

    // then
    assertThat(result.size()).isEqualTo(2L);
    assertThat(result.get(0).getReviewId()).isEqualTo(1L);
    assertThat(result.get(0).getThumbnailId()).isEqualTo(1L);
    assertThat(result.get(1).getReviewId()).isEqualTo(1L);
    assertThat(result.get(1).getThumbnailId()).isEqualTo(2L);
  }
}
