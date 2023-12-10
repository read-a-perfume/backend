package io.perfume.api.review.adapter.out.persistence.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.review.application.out.like.ReviewLikeQueryRepository;
import io.perfume.api.review.application.out.like.dto.ReviewLikeCount;
import io.perfume.api.review.domain.ReviewLike;
import jakarta.persistence.EntityManager;

import java.util.List;
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
        return Optional.ofNullable(jpaQueryFactory.selectFrom(QReviewLikeEntity.reviewLikeEntity)
                        .where(
                                QReviewLikeEntity.reviewLikeEntity.userId.eq(userId)
                                        .and(QReviewLikeEntity.reviewLikeEntity.reviewId.eq(reviewId))
                                        .and(QReviewLikeEntity.reviewLikeEntity.deletedAt.isNull())
                        )
                        .fetchOne())
                .map(reviewLikeMapper::toDomain);
    }

    @Override
    public long countByReviewId(long reviewId) {
        return entityManager.createQuery(
                        "select count(rl) from ReviewLikeEntity rl where rl.reviewId = :reviewId", Long.class)
                .setParameter("reviewId", reviewId)
                .getSingleResult();
    }

    @Override
    public List<ReviewLikeCount> countByReviewIds(List<Long> reviewIds) {
        return jpaQueryFactory.select(QReviewLikeEntity.reviewLikeEntity.reviewId, QReviewLikeEntity.reviewLikeEntity.count())
                .from(QReviewLikeEntity.reviewLikeEntity)
                .where(QReviewLikeEntity.reviewLikeEntity.reviewId.in(reviewIds).and(QReviewLikeEntity.reviewLikeEntity.deletedAt.isNull()))
                .groupBy(QReviewLikeEntity.reviewLikeEntity.reviewId)
                .fetch()
                .stream()
                .filter(tuple -> tuple.get(QReviewLikeEntity.reviewLikeEntity.reviewId) != null && tuple.get(QReviewLikeEntity.reviewLikeEntity.count()) != null)
                .map(tuple -> new ReviewLikeCount(tuple.get(QReviewLikeEntity.reviewLikeEntity.reviewId), tuple.get(QReviewLikeEntity.reviewLikeEntity.count())))
                .toList();
    }
}
