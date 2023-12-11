package io.perfume.api.review.application.service;

import io.perfume.api.auth.adapter.out.redis.RedisRepository;
import io.perfume.api.review.application.in.dto.ReviewTagResult;
import io.perfume.api.review.application.out.tag.TagQueryRepository;
import io.perfume.api.review.application.out.tag.TagRepository;
import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTagService {
  private final RedisRepository redisRepository;
  private final TagQueryRepository tagQueryRepository;
  private final TagRepository tagRepository;

  public List<Tag> addTags(Long reviewId, List<Long> tagIds) {
    var now = LocalDateTime.now();
    var tags = tagQueryRepository.findByIds(tagIds);
    var entities = tags.stream().map(tag -> ReviewTag.create(reviewId, tag.getId(), now)).toList();

    tagRepository.saveAll(entities);

    return tags;
  }

  public void deleteReviewTag(Long reviewId, LocalDateTime now) {
    var reviewTags =
        tagQueryRepository.findReviewTags(reviewId).stream()
            .peek(reviewTag -> reviewTag.markDelete(now))
            .toList();

    tagRepository.saveAll(reviewTags);
  }

  public List<ReviewTagResult> getReviewTags(final Long reviewId) {
    return getReviewTags(Collections.singletonList(reviewId));
  }

  public List<ReviewTagResult> getReviewsTags(final List<Long> reviewIds) {
    return getReviewTags(reviewIds);
  }

  private List<ReviewTagResult> getReviewTags(final List<Long> reviewIds) {
    final List<ReviewTag> reviewTags = tagQueryRepository.findReviewsTags(reviewIds);
    final List<Long> tagIds = reviewTags.stream().map(ReviewTag::getTagId).toList();
    final Map<Long, ReviewTag> reviewTagMap =
        reviewTags.stream()
            .collect(Collectors.toMap(ReviewTag::getTagId, Function.identity(), (a, b) -> a));

    return tagQueryRepository.findByIds(tagIds).stream()
        .map(toReviewTagResult(reviewTagMap))
        .toList();
  }

  private Function<Tag, ReviewTagResult> toReviewTagResult(final Map<Long, ReviewTag> reviewTags) {
    return tag -> ReviewTagResult.from(tag, reviewTags.get(tag.getId()));
  }
}
