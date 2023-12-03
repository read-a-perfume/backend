package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.DeleteReviewTagUseCase;
import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.application.out.TagRepository;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class DeleteReviewTagService implements DeleteReviewTagUseCase {

  private final TagRepository tagRepository;

  private final TagQueryRepository tagQueryRepository;

  public DeleteReviewTagService(
      TagRepository tagRepository, TagQueryRepository tagQueryRepository) {
    this.tagRepository = tagRepository;
    this.tagQueryRepository = tagQueryRepository;
  }

  @Override
  public void deleteReviewTag(Long reviewId, @NotNull LocalDateTime now) {
    var reviewTags =
        tagQueryRepository.findReviewTags(reviewId).stream()
            .peek(reviewTag -> reviewTag.markDelete(now))
            .toList();

    tagRepository.saveAll(reviewTags);
  }
}
