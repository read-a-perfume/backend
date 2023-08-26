package io.perfume.api.review.adapter.out.persistence.repository;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewTagMapper;
import io.perfume.api.review.application.out.ReviewTagRepository;
import io.perfume.api.review.application.out.TagRepository;
import io.perfume.api.review.domain.ReviewTag;
import java.util.List;
import java.util.stream.StreamSupport;

@PersistenceAdapter
public class TagPersistenceAdapter implements TagRepository {

  private final ReviewTagRepository reviewTagRepository;

  private final ReviewTagMapper reviewTagMapper;

  public TagPersistenceAdapter(ReviewTagRepository reviewTagRepository,
                               ReviewTagMapper reviewTagMapper) {
    this.reviewTagRepository = reviewTagRepository;
    this.reviewTagMapper = reviewTagMapper;
  }

  @Override
  public ReviewTag save(ReviewTag tags) {
    var created = reviewTagRepository.save(reviewTagMapper.toEntity(tags));

    return reviewTagMapper.toDomain(created);
  }

  @Override
  public List<ReviewTag> saveAll(List<ReviewTag> tags) {
    var entities = tags.stream().map(reviewTagMapper::toEntity).toList();
    var createdReviewTags = reviewTagRepository.saveAll(entities);

    return StreamSupport
        .stream(createdReviewTags.spliterator(), true)
        .map(reviewTagMapper::toDomain)
        .toList();
  }
}
