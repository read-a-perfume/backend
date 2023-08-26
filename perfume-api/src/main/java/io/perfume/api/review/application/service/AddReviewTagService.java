package io.perfume.api.review.application.service;

import io.perfume.api.review.application.in.AddReviewTagUseCase;
import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.application.out.TagRepository;
import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddReviewTagService implements AddReviewTagUseCase {

  private final TagRepository tagRepository;

  private final TagQueryRepository tagQueryRepository;

  public AddReviewTagService(TagRepository tagRepository, TagQueryRepository tagQueryRepository) {
    this.tagRepository = tagRepository;
    this.tagQueryRepository = tagQueryRepository;
  }

  @Override
  @Transactional
  public List<Tag> addTags(Long reviewId, List<Long> tagIds) {
    var now = LocalDateTime.now();
    var tags = tagQueryRepository.findByIds(tagIds);
    var entities = tags.stream().map(tag -> ReviewTag.create(reviewId, tag.getId(), now)).toList();

    tagRepository.saveAll(entities);

    return tags;
  }
}
