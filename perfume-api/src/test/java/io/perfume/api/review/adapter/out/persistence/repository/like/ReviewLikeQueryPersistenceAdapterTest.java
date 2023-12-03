package io.perfume.api.review.adapter.out.persistence.repository.like;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewLikeEntity;
import io.perfume.api.review.adapter.out.persistence.repository.ReviewMapper;
import io.perfume.api.review.domain.ReviewLike;
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
@Import({
  ReviewLikeQueryPersistenceAdapter.class,
  TestQueryDSLConfiguration.class,
  ReviewLikeMapper.class,
  ReviewMapper.class
})
@DataJpaTest
@EnableJpaAuditing
class ReviewLikeQueryPersistenceAdapterTest {

  @Autowired private ReviewLikeQueryPersistenceAdapter repository;

  @Autowired private ReviewLikeMapper reviewLikeMapper;

  @Autowired private ReviewMapper reviewMapper;

  @Autowired private EntityManager entityManager;

  @Test
  @DisplayName("리뷰를 조회한다.")
  void testFindById() {
    // given
    final var now = LocalDateTime.now();
    ReviewLike reviewLike =
        reviewLikeMapper.toDomain(new ReviewLikeEntity(null, 1L, 1L, now, now, null));
    entityManager.persist(reviewLikeMapper.toEntity(reviewLike));

    // when
    final var result = repository.findByUserIdAndReviewId(1L, 1L);

    // then
    assertThat(result).isPresent();
  }

  @Test
  @DisplayName("존재하지 않는 좋아요를 조회 시 빈 값이 반환된다.")
  void testFindEmptyReviewLike() {
    // given
    final var now = LocalDateTime.now();
    ReviewLike reviewLike =
        reviewLikeMapper.toDomain(new ReviewLikeEntity(null, 1L, 1L, now, now, null));
    entityManager.persist(reviewLikeMapper.toEntity(reviewLike));

    // when
    final var result = repository.findByUserIdAndReviewId(1L, 2L);

    // then
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("리뷰 좋아요 수를 조회한다.")
  void testCountByReviewId() {
    // given
    final var now = LocalDateTime.now();
    ReviewLike reviewLike =
        reviewLikeMapper.toDomain(new ReviewLikeEntity(null, 1L, 1L, now, now, null));
    entityManager.persist(reviewLikeMapper.toEntity(reviewLike));

    // when
    final var result = repository.countByReviewId(1L);

    // then
    assertThat(result).isEqualTo(1L);
  }
}
