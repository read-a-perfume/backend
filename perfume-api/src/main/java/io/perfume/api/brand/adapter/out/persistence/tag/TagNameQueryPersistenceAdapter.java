package io.perfume.api.brand.adapter.out.persistence.tag;

import static io.perfume.api.brand.adapter.out.persistence.tag.QTagNameEntity.tagNameEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.TagNameQueryRepository;
import io.perfume.api.brand.domain.TagName;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TagNameQueryPersistenceAdapter implements TagNameQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final TagNameMapper tagNameMapper;

  @Override
  public Optional<TagName> findByName(String name) {
    TagNameEntity entity =
        jpaQueryFactory
            .selectFrom(tagNameEntity)
            .where(tagNameEntity.name.eq(name).and(tagNameEntity.deletedAt.isNull()))
            .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(tagNameMapper.toDomain(entity));
  }

  @Override
  public List<TagName> findTagsByName(List<String> names) {
    return jpaQueryFactory
        .selectFrom(tagNameEntity)
        .where(tagNameEntity.name.in(names))
        .fetch()
        .stream()
        .map(tagNameMapper::toDomain)
        .toList();
  }

  @Override
  public List<TagName> findTagsByIds(List<Long> tagIds) {
    return jpaQueryFactory
        .selectFrom(tagNameEntity)
        .where(tagNameEntity.id.in(tagIds))
        .fetch()
        .stream()
        .map(tagNameMapper::toDomain)
        .toList();
  }
}
