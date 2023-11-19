package io.perfume.api.review.adapter.out.persistence.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.review.adapter.out.persistence.repository.tag.TagMapper;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
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
  private ReviewQueryRepository queryRepository;

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
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        1L,
        Season.SPRING,
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
