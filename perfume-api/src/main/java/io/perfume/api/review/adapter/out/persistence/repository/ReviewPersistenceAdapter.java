package io.perfume.api.review.adapter.out.persistence.repository;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.domain.Review;

@PersistenceAdapter
public class ReviewPersistenceAdapter implements ReviewRepository {

  private final ReviewJpaRepository reviewRepository;

  private final ReviewMapper reviewMapper;

  public ReviewPersistenceAdapter(ReviewJpaRepository reviewRepository, ReviewMapper reviewMapper) {
    this.reviewRepository = reviewRepository;
    this.reviewMapper = reviewMapper;
  }

  public Review save(Review review) {
    var createdEntity = reviewRepository.save(reviewMapper.toEntity(review));

    return reviewMapper.toDomain(createdEntity);
  }
}
