package io.perfume.api.review.adapter.out.persistence.repository;

import static io.perfume.api.review.adapter.out.persistence.entity.QTagEntity.tagEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.adapter.out.persistence.mapper.TagMapper;
import io.perfume.api.review.application.out.TagQueryRepository;
import io.perfume.api.review.domain.Tag;
import java.util.List;

@PersistenceAdapter
public class TagQueryPersistenceAdapter implements TagQueryRepository {

  private final TagMapper tagMapper;

  private final JPAQueryFactory jpaQueryFactory;

  public TagQueryPersistenceAdapter(TagMapper tagMapper, JPAQueryFactory jpaQueryFactory) {
    this.tagMapper = tagMapper;
    this.jpaQueryFactory = jpaQueryFactory;
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
}
