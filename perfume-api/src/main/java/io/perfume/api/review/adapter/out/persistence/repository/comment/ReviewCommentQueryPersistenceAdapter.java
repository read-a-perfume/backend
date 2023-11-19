package io.perfume.api.review.adapter.out.persistence.repository.comment;

import static io.perfume.api.review.adapter.out.persistence.entity.QReviewCommentEntity.reviewCommentEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dto.repository.CursorDirection;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.adapter.out.persistence.entity.ReviewCommentEntity;
import io.perfume.api.review.application.out.ReviewCommentQueryRepository;
import io.perfume.api.review.domain.ReviewComment;
import java.util.Optional;

@PersistenceAdapter
public class ReviewCommentQueryPersistenceAdapter implements ReviewCommentQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final ReviewCommentMapper reviewCommentMapper;

  public ReviewCommentQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory,
                                              ReviewCommentMapper reviewCommentMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.reviewCommentMapper = reviewCommentMapper;
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
