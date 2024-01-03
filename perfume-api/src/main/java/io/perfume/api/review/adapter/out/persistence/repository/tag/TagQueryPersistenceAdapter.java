package io.perfume.api.review.adapter.out.persistence.repository.tag;

import static io.perfume.api.review.adapter.out.persistence.repository.tag.QTagEntity.tagEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.review.application.out.tag.TagQueryRepository;
import io.perfume.api.review.domain.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class TagQueryPersistenceAdapter implements TagQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final TagMapper tagMapper;

  @Override
  public List<Tag> findAll() {
    return jpaQueryFactory.selectFrom(tagEntity).fetch().stream().map(tagMapper::toDomain).toList();
  }

  @Override
  public List<Tag> findByIds(List<Long> ids) {
    return jpaQueryFactory.selectFrom(tagEntity).where(tagEntity.id.in(ids)).fetch().stream()
        .map(tagMapper::toDomain)
        .toList();
  }
}
