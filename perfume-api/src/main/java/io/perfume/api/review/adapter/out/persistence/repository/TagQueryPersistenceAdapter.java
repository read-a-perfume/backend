package io.perfume.api.review.adapter.out.persistence.repository;

import static io.perfume.api.review.adapter.out.persistence.entity.QReviewTagEntity.reviewTagEntity;
import static io.perfume.api.review.adapter.out.persistence.entity.QTagEntity.tagEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.adapter.out.persistence.mapper.ReviewTagMapper;
import io.perfume.api.review.adapter.out.persistence.mapper.TagMapper;
import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import java.util.List;

@PersistenceAdapter
public class TagQueryPersistenceAdapter implements TagQueryRepository {

  private final TagMapper tagMapper;

  private final ReviewTagMapper reviewTagMapper;

  private final JPAQueryFactory jpaQueryFactory;

  public TagQueryPersistenceAdapter(TagMapper tagMapper, ReviewTagMapper reviewTagMapper,
                                    JPAQueryFactory jpaQueryFactory) {
    this.tagMapper = tagMapper;
    this.jpaQueryFactory = jpaQueryFactory;
    this.reviewTagMapper = reviewTagMapper;
  }

  @Override
  public List<Tag> findByIds(List<Long> ids) {
    return jpaQueryFactory.selectFrom(tagEntity)
        .where(tagEntity.id.in(ids))
        .fetch()
        .stream()
        .map(tagMapper::toDomain)
        .toList();
  }

  @Override
  public List<ReviewTag> findReviewTags(Long reviewId) {
    return jpaQueryFactory.selectFrom(reviewTagEntity)
        .where(reviewTagEntity.id.reviewId.eq(reviewId))
        .fetch()
        .stream()
        .map(reviewTagMapper::toDomain)
        .toList();
  }
}
