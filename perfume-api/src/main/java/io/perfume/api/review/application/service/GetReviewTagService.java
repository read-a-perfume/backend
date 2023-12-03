package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.GetReviewTagUseCase;
import io.perfume.api.review.application.in.dto.ReviewTagResult;
import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class GetReviewTagService implements GetReviewTagUseCase {

  private final TagQueryRepository tagQueryRepository;

  public GetReviewTagService(TagQueryRepository tagQueryRepository) {
    this.tagQueryRepository = tagQueryRepository;
  }

  @Override
  public List<ReviewTagResult> getReviewTags(Long reviewId) {
    final var reviewTags =
        tagQueryRepository.findReviewTags(reviewId).stream()
            .collect(Collectors.toMap(ReviewTag::getTagId, Function.identity()));
    final var tagIds = reviewTags.keySet().stream().toList();

    return tagQueryRepository.findByIds(tagIds).stream()
        .map(toReviewTagResult(reviewTags))
        .toList();
  }

  @Override
  public List<ReviewTagResult> getReviewsTags(List<Long> reviewIds) {
    final var reviewTags =
        tagQueryRepository.findReviewsTags(reviewIds).stream()
            .collect(Collectors.toMap(ReviewTag::getTagId, Function.identity()));
    final var tagIds = reviewTags.keySet().stream().toList();

    return tagQueryRepository.findByIds(tagIds).stream()
        .map(toReviewTagResult(reviewTags))
        .toList();
  }

  @NotNull
  private Function<Tag, ReviewTagResult> toReviewTagResult(Map<Long, ReviewTag> reviewTags) {
    return tag -> ReviewTagResult.from(tag, reviewTags.get(tag.getId()));
  }
}
