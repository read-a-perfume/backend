package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.dto.ReviewTagResult;
import io.perfume.api.review.application.out.tag.TagQueryRepository;
import io.perfume.api.review.application.out.tag.TagRepository;
import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTagService {
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
                tagQueryRepository
                        .findReviewTags(reviewId).stream()
                        .peek(reviewTag -> reviewTag.markDelete(now))
                        .toList();

        tagRepository.saveAll(reviewTags);
    }

    public List<ReviewTagResult> getReviewTags(Long reviewId) {
        final var reviewTags =
                tagQueryRepository.findReviewTags(reviewId)
                        .stream()
                        .collect(Collectors.toMap(ReviewTag::getTagId, Function.identity()));
        final var tagIds = reviewTags.keySet().stream().toList();

        return tagQueryRepository.findByIds(tagIds)
                .stream()
                .map(toReviewTagResult(reviewTags))
                .toList();
    }

    public List<ReviewTagResult> getReviewsTags(List<Long> reviewIds) {
        final var reviewTags =
                tagQueryRepository.findReviewsTags(reviewIds)
                        .stream()
                        .collect(Collectors.toMap(ReviewTag::getTagId, Function.identity()));
        final var tagIds = reviewTags.keySet().stream().toList();

        return tagQueryRepository.findByIds(tagIds)
                .stream()
                .map(toReviewTagResult(reviewTags))
                .toList();
    }

    @NotNull
    private Function<Tag, ReviewTagResult> toReviewTagResult(Map<Long, ReviewTag> reviewTags) {
        return tag -> ReviewTagResult.from(tag, reviewTags.get(tag.getId()));
    }
}
