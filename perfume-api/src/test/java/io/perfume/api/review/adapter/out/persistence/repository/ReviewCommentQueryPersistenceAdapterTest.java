package io.perfume.api.review.adapter.out.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import dto.repository.CursorDirection;
import dto.repository.CursorPageable;
import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewEntity;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewCommentMapper;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewMapper;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewComment;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({ReviewCommentQueryPersistenceAdapter.class, TestQueryDSLConfiguration.class,
    ReviewCommentMapper.class, ReviewMapper.class})
@DataJpaTest
@EnableJpaAuditing
class ReviewCommentQueryPersistenceAdapterTest {

  @Autowired
  private ReviewCommentQueryPersistenceAdapter repository;

  @Autowired
  private ReviewMapper reviewMapper;

  @Autowired
  private ReviewCommentMapper reviewCommentMapper;

  @Autowired
  private EntityManager entityManager;

  @Test
  @DisplayName("전방 커서를 이용하여 리뷰 댓글을 조회한다.")
  void 전방_리뷰_댓글_조회() {
    // given
    final var now = LocalDateTime.now();
    ReviewEntity review = reviewMapper.toEntity(Review.create(
        "test",
        "test description",
        STRENGTH.LIGHT,
        1000L,
        SEASON.DAILY,
        1L,
        1L,
        now
    ));
    entityManager.persist(review);

    final var comments = Stream.of(
        ReviewComment.create(review.getId(), 1L, "test1", now),
        ReviewComment.create(review.getId(), 1L, "test2", now),
        ReviewComment.create(review.getId(), 1L, "test3", now),
        ReviewComment.create(review.getId(), 1L, "test4", now),
        ReviewComment.create(review.getId(), 1L, "test5", now),
        ReviewComment.create(review.getId(), 1L, "test6", now)
    ).map(reviewCommentMapper::toEntity).toList();
    comments.forEach(entityManager::persist);
    entityManager.flush();

    final var pageable = new CursorPageable<>(10, CursorDirection.NEXT, comments.get(3).getId());

    // when
    var result = repository.findByReviewId(pageable, review.getId());

    // then
    assertThat(result.getItems()).hasSize(2);
    assertThat(result.getItems().get(0).getContent()).isEqualTo("test5");
    assertThat(result.getItems().get(1).getContent()).isEqualTo("test6");
  }

  @Test
  @DisplayName("후방 커서를 이용하여 리뷰 댓글을 조회한다.")
  void 후방_리뷰_댓글_조회() {
    // given
    final var now = LocalDateTime.now();
    ReviewEntity review = reviewMapper.toEntity(Review.create(
        "test",
        "test description",
        STRENGTH.LIGHT,
        1000L,
        SEASON.DAILY,
        1L,
        1L,
        now
    ));
    entityManager.persist(review);

    final var comments = Stream.of(
        ReviewComment.create(review.getId(), 1L, "test1", now),
        ReviewComment.create(review.getId(), 1L, "test2", now),
        ReviewComment.create(review.getId(), 1L, "test3", now),
        ReviewComment.create(review.getId(), 1L, "test4", now),
        ReviewComment.create(review.getId(), 1L, "test5", now),
        ReviewComment.create(review.getId(), 1L, "test6", now)
    ).map(reviewCommentMapper::toEntity).toList();
    comments.forEach(entityManager::persist);
    entityManager.flush();

    final var pageable = new CursorPageable<>(10, CursorDirection.PREVIOUS, comments.get(3).getId());

    // when
    var result = repository.findByReviewId(pageable, review.getId());

    // then
    assertThat(result.getItems()).hasSize(3);
    assertThat(result.getItems().get(0).getContent()).isEqualTo("test1");
    assertThat(result.getItems().get(1).getContent()).isEqualTo("test2");
    assertThat(result.getItems().get(2).getContent()).isEqualTo("test3");
  }
}
