package io.perfume.api.review.adapter.out.persistence.repository.comment;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.ReviewCommentRepository;
import io.perfume.api.review.domain.ReviewComment;

@PersistenceAdapter
public class ReviewCommentPersistenceAdapter implements ReviewCommentRepository {

  private final ReviewCommentJpaRepository reviewCommentJpaRepository;

  private final ReviewCommentMapper reviewCommentMapper;

  public ReviewCommentPersistenceAdapter(
      ReviewCommentJpaRepository reviewCommentJpaRepository,
      ReviewCommentMapper reviewCommentMapper) {
    this.reviewCommentJpaRepository = reviewCommentJpaRepository;
    this.reviewCommentMapper = reviewCommentMapper;
  }

  @Override
  public ReviewComment save(ReviewComment domain) {
    return reviewCommentMapper.toDomain(
        reviewCommentJpaRepository.save(reviewCommentMapper.toEntity(domain)));
  }
}
