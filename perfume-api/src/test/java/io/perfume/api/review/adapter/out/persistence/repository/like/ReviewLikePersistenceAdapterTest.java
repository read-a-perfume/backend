package io.perfume.api.review.adapter.out.persistence.repository.like;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewLikeEntity;
import io.perfume.api.review.adapter.out.persistence.repository.like.ReviewLikeMapper;
import io.perfume.api.review.adapter.out.persistence.repository.like.ReviewLikePersistenceAdapter;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({ReviewLikePersistenceAdapter.class, ReviewLikeMapper.class})
@DataJpaTest
class ReviewLikePersistenceAdapterTest {

  @Autowired
  private ReviewLikePersistenceAdapter repository;

  @Autowired
  private ReviewLikeMapper mapper;

  @Test
  @DisplayName("리뷰 좋아요 데이터 저장")
  void testSave() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final long userId = 1L;
    final long reviewId = 1L;
    final ReviewLikeEntity entity = new ReviewLikeEntity(null, userId, reviewId, now, now, null);

    // when
    var expectReviewLike = repository.save(mapper.toDomain(entity));

    // then
    assertThat(expectReviewLike.getUserId()).isEqualTo(userId);
    assertThat(expectReviewLike.getReviewId()).isEqualTo(reviewId);
  }
}
