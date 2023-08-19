package io.perfume.api.review.adapter.out.persistence.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewMapper;
import io.perfume.api.review.adapter.out.persistence.mapper.TagMapper;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({ReviewQueryPersistenceAdapter.class, ReviewMapper.class, TagMapper.class,
    TestQueryDSLConfiguration.class})
@DataJpaTest
@EnableJpaAuditing
class ReviewQueryPersistenceAdapterTest {

  @Autowired
  private ReviewQueryPersistenceAdapter queryRepository;

  @Autowired
  private ReviewMapper reviewMapper;

  @Autowired
  private TagMapper tagMapper;

  @Autowired
  private EntityManager entityManager;

  @Test
  @DisplayName("존재하지 않는 데이터를 조회할 경우 빈 객체를 반환한다.")
  void testNotFoundReview() {
    // given

    // when
    var result = queryRepository.findById(1L);

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void testFindById() {
    // given
    var now = LocalDateTime.now();
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
    var createdReview = reviewMapper.toEntity(review);
    entityManager.persist(createdReview);
    entityManager.flush();
    entityManager.clear();

    // when
    var result = queryRepository.findById(1L).orElseThrow();

    // then
    assertThat(result.getId()).isGreaterThan(0L);
  }
}
