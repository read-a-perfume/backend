package io.perfume.api.review.adapter.out.persistence.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.review.adapter.out.persistence.entity.QReviewLikeEntity;
import io.perfume.api.review.application.out.ReviewLikeQueryRepository;
import io.perfume.api.review.domain.ReviewLike;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewLikeQueryPersistenceAdapter implements ReviewLikeQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final ReviewLikeMapper reviewLikeMapper;
  private final EntityManager entityManager;

  @Override
  public Optional<ReviewLike> findByUserIdAndReviewId(long userId, long reviewId) {
    return Optional.ofNullable(
            jpaQueryFactory
                .selectFrom(QReviewLikeEntity.reviewLikeEntity)
                .where(
                    QReviewLikeEntity.reviewLikeEntity
                        .userId
                        .eq(userId)
                        .and(QReviewLikeEntity.reviewLikeEntity.reviewId.eq(reviewId))
                        .and(QReviewLikeEntity.reviewLikeEntity.deletedAt.isNull()))
                .fetchOne())
        .map(reviewLikeMapper::toDomain);
  }

  @Override
  public long countByReviewId(long reviewId) {
    return entityManager
        .createQuery(
            "select count(rl) from ReviewLikeEntity rl where rl.reviewId = :reviewId", Long.class)
        .setParameter("reviewId", reviewId)
        .getSingleResult();
  }
}
