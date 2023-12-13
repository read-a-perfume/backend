package io.perfume.api.review.adapter.out.persistence.repository.comment;

import static io.perfume.api.review.adapter.out.persistence.repository.comment.QReviewCommentEntity.reviewCommentEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dto.repository.CursorPageable;
import dto.repository.CursorPagination;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.comment.ReviewCommentQueryRepository;
import io.perfume.api.review.application.out.comment.dto.ReviewCommentCount;
import io.perfume.api.review.domain.ReviewComment;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReviewCommentQueryPersistenceAdapter implements ReviewCommentQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final ReviewCommentMapper reviewCommentMapper;
  private final EntityManager entityManager;

  @Override
  public long countByReviewId(long reviewId) {
    return entityManager
        .createQuery(
            "select count(1) from ReviewCommentEntity where reviewId = :reviewId and deletedAt is null",
            Long.class)
        .setParameter("reviewId", reviewId)
        .getSingleResult();
  }

  @Override
  public Optional<ReviewComment> findById(Long id) {
    ReviewCommentEntity entity =
        jpaQueryFactory
            .selectFrom(reviewCommentEntity)
            .where(reviewCommentEntity.id.eq(id).and(reviewCommentEntity.deletedAt.isNull()))
            .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(reviewCommentMapper.toDomain(entity));
  }

  @Override
  public CursorPagination<ReviewComment> findByReviewId(
      CursorPageable pageable, final long reviewId) {
    final var qb =
        jpaQueryFactory
            .selectFrom(reviewCommentEntity)
            .where(
                reviewCommentEntity
                    .reviewId
                    .eq(reviewId)
                    .and(reviewCommentEntity.deletedAt.isNull()));

    final Optional<Long> cursor = pageable.getCursor(Long::parseLong);
    if (cursor.isPresent()) {
      final long id = cursor.get();
      if (pageable.isNext()) {
        qb.where(reviewCommentEntity.id.gt(id));
      } else {
        qb.where(reviewCommentEntity.id.lt(id));
      }
    }

    final var comments =
        qb.limit(pageable.getFetchSize()).fetch().stream()
            .map(reviewCommentMapper::toDomain)
            .toList();

    return CursorPagination.of(
        comments, pageable, (reviewComment) -> reviewComment.getId().toString());
  }

  @Override
  public List<ReviewCommentCount> countByReviewIds(List<Long> reviewIds) {
    return jpaQueryFactory
        .select(reviewCommentEntity.reviewId, reviewCommentEntity.count())
        .from(reviewCommentEntity)
        .where(reviewCommentEntity.reviewId.in(reviewIds))
        .groupBy(reviewCommentEntity.reviewId)
        .fetch()
        .stream()
        .map(
            tuple ->
                new ReviewCommentCount(
                    tuple.get(reviewCommentEntity.reviewId),
                    tuple.get(reviewCommentEntity.count())))
        .toList();
  }
}
