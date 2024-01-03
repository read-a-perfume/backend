package io.perfume.api.review.adapter.out.persistence.repository.reviewtag;

import static io.perfume.api.review.adapter.out.persistence.repository.reviewtag.QReviewTagEntity.reviewTagEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.tag.ReviewTagQueryRepository;
import io.perfume.api.review.domain.ReviewTag;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReviewTagQueryPersistenceAdapter implements ReviewTagQueryRepository {

  private final ReviewTagMapper reviewTagMapper;

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ReviewTag> findReviewTags(Long reviewId) {
    return jpaQueryFactory
        .selectFrom(reviewTagEntity)
        .where(reviewTagEntity.id.reviewId.eq(reviewId))
        .fetch()
        .stream()
        .map(reviewTagMapper::toDomain)
        .toList();
  }

  @Override
  public List<ReviewTag> findReviewsTags(List<Long> reviewIds) {
    return jpaQueryFactory
        .selectFrom(reviewTagEntity)
        .where(reviewTagEntity.id.reviewId.in(reviewIds))
        .fetch()
        .stream()
        .map(reviewTagMapper::toDomain)
        .toList();
  }
}
