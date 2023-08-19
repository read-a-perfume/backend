package io.perfume.api.review.adapter.out.persistence.repository;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewTagMapper;
import io.perfume.api.review.application.out.ReviewTagRepository;
import io.perfume.api.review.application.out.TagRepository;
import io.perfume.api.review.domain.ReviewTag;

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
}
