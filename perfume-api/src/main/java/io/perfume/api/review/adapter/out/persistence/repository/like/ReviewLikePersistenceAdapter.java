package io.perfume.api.review.adapter.out.persistence.repository.like;

import io.perfume.api.review.application.out.like.ReviewLikeRepository;
import io.perfume.api.review.domain.ReviewLike;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewLikePersistenceAdapter implements ReviewLikeRepository {

  private final ReviewLikeJpaRepository repository;

  private final ReviewLikeMapper mapper;

  public ReviewLikePersistenceAdapter(ReviewLikeJpaRepository repository, ReviewLikeMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public ReviewLike save(ReviewLike review) {
    var created = repository.save(mapper.toEntity(review));

    return mapper.toDomain(created);
  }
}
