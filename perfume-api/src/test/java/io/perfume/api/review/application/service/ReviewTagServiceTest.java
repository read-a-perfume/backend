package io.perfume.api.review.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.review.adapter.out.persistence.repository.reviewtag.ReviewTagEntity;
import io.perfume.api.review.adapter.out.persistence.repository.tag.TagEntity;
import io.perfume.api.review.application.in.dto.ReviewTagResult;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@SuppressWarnings("NonAsciiCharacters")
class ReviewTagServiceTest {

  @Autowired private ReviewTagService reviewTagService;

  @Autowired private EntityManager entityManager;

  @Test
  void 리뷰_태그_조회() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final List<TagEntity> tags =
        List.of(
            new TagEntity(null, "태그1", now, now, null), new TagEntity(null, "태그2", now, now, null));
    tags.stream().forEach(entityManager::persist);
    List.of(
            new ReviewTagEntity(1L, tags.get(0).getId(), now, now, null),
            new ReviewTagEntity(1L, tags.get(1).getId(), now, now, null))
        .forEach(entityManager::persist);
    entityManager.flush();

    // when
    final List<ReviewTagResult> result = reviewTagService.getReviewTags(1L);

    // then
    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  void 리뷰_목록_태그_조회() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final List<TagEntity> tags =
        List.of(
            new TagEntity(null, "태그1", now, now, null), new TagEntity(null, "태그2", now, now, null));
    tags.stream().forEach(entityManager::persist);
    List.of(
            new ReviewTagEntity(1L, tags.get(0).getId(), now, now, null),
            new ReviewTagEntity(1L, tags.get(1).getId(), now, now, null),
            new ReviewTagEntity(2L, tags.get(0).getId(), now, now, null),
            new ReviewTagEntity(2L, tags.get(1).getId(), now, now, null))
        .forEach(entityManager::persist);
    entityManager.flush();

    // when
    final List<ReviewTagResult> result = reviewTagService.getReviewsTags(List.of(1L, 2L));

    // then
    assertThat(result.size()).isEqualTo(2);
  }
}
