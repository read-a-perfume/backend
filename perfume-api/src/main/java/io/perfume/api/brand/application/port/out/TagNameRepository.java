package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.domain.TagName;
import java.util.List;

public interface TagNameRepository {

  TagName save(TagName tagName);

  List<TagName> saveAll(List<TagName> tagNames);
}
