package io.perfume.api.brand.adapter.out.persistence.tag;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.TagNameRepository;
import io.perfume.api.brand.domain.TagName;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TagNamePersistenceAdapter implements TagNameRepository {

  private final TagNameMapper tagNameMapper;

  private final TagNameJpaRepository tagNameJpaRepository;

  @Override
  public TagName save(TagName tagName) {
    TagNameEntity tagNameEntity = tagNameJpaRepository.save(tagNameMapper.toEntity(tagName));
    return tagNameMapper.toDomain(tagNameEntity);
  }

  @Override
  public List<TagName> saveAll(List<TagName> tagNames) {
    var entities = tagNames.stream().map(tagNameMapper::toEntity).toList();
    return StreamSupport.stream(tagNameJpaRepository.saveAll(entities).spliterator(), true)
        .map(tagNameMapper::toDomain)
        .toList();
  }
}
