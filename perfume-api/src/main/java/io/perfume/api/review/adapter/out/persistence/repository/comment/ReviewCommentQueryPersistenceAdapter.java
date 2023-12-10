package io.perfume.api.review.adapter.out.persistence.repository.comment;


import com.querydsl.jpa.impl.JPAQueryFactory;
import dto.repository.CursorDirection;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.comment.ReviewCommentQueryRepository;
import io.perfume.api.review.domain.ReviewComment;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import static io.perfume.api.review.adapter.out.persistence.repository.comment.QReviewCommentEntity.reviewCommentEntity;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReviewCommentQueryPersistenceAdapter implements ReviewCommentQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final ReviewCommentMapper reviewCommentMapper;
  private final EntityManager entityManager;

  @Override
  public long countByReviewId(long reviewId) {
    return entityManager.createQuery(
            "select count(1) from ReviewCommentEntity where reviewId = :reviewId and deletedAt is null",
            Long.class)
        .setParameter("reviewId", reviewId)
        .getSingleResult();
  }

  @Override
  public Optional<ReviewComment> findById(Long id) {
    ReviewCommentEntity entity = jpaQueryFactory.selectFrom(reviewCommentEntity)
        .where(reviewCommentEntity.id.eq(id).and(reviewCommentEntity.deletedAt.isNull()))
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(reviewCommentMapper.toDomain(entity));
  }

  @Override
  public CursorPagination<ReviewComment> findByReviewId(CursorPageable<Long> pageable,
                                                        final long reviewId) {
    final var qb = jpaQueryFactory.selectFrom(reviewCommentEntity)
        .where(reviewCommentEntity.reviewId.eq(reviewId)
            .and(reviewCommentEntity.deletedAt.isNull()));

    if (pageable.getCursor() != null) {
      if (pageable.getDirection() == CursorDirection.NEXT) {
        qb.where(reviewCommentEntity.id.gt(pageable.getCursor()));
      } else {
        qb.where(reviewCommentEntity.id.lt(pageable.getCursor()));
      }
    }

    final var comments = qb
        .limit(pageable.getSize())
        .fetch()
        .stream()
        .map(reviewCommentMapper::toDomain)
        .toList();

    return CursorPagination.of(comments, pageable.getSize(), pageable.getDirection(),
        pageable.getCursor() != null);
  }
}
