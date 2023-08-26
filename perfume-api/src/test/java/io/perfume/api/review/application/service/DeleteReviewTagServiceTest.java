package io.perfume.api.review.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.stub.StubTagRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteReviewTagServiceTest {

  private final StubTagRepository stubTagRepository =
      new StubTagRepository();

  private final DeleteReviewTagService deleteReviewTagService =
      new DeleteReviewTagService(stubTagRepository, stubTagRepository);

  @BeforeEach
  void setUp() {
    stubTagRepository.clear();
  }

  @Test
  void deleteReviewTag() {
    // given
    var now = LocalDateTime.now();
    var reviewTag = new ReviewTag(1L, 1L, now, now, null);
    stubTagRepository.addReviewTag(reviewTag);

    // when
    deleteReviewTagService.deleteReviewTag(1L, now);

    // then
    assertThat(reviewTag.getDeletedAt()).isNotNull();
  }
}
