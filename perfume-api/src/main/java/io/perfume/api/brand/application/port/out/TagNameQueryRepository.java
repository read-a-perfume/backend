package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.TagName;
import java.util.List;
import java.util.Optional;

public interface TagNameQueryRepository {

  Optional<TagName> findByName(String name);

  List<TagName> findTagsByName(List<String> names);

  List<TagName> findTagsByIds(List<Long> tagIds);
}
