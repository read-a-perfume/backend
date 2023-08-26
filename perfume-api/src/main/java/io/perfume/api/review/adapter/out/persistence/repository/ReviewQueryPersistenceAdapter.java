package io.perfume.api.review.adapter.out.persistence.repository;

import static io.perfume.api.review.adapter.out.persistence.entity.QReviewEntity.reviewEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewMapper;
import io.perfume.api.review.application.out.ReviewQueryRepository;
import io.perfume.api.review.domain.Review;
import java.util.Optional;

@PersistenceAdapter
public class ReviewQueryPersistenceAdapter implements ReviewQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final ReviewMapper reviewMapper;

  public ReviewQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory, ReviewMapper reviewMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.reviewMapper = reviewMapper;
  }

  public Optional<Review> findById(Long id) {
    var entity = jpaQueryFactory
        .selectFrom(reviewEntity)
        .where(
            reviewEntity.id.eq(id)
                .and(reviewEntity.deletedAt.isNull()))
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(reviewMapper.toDomain(entity));
  }
}
