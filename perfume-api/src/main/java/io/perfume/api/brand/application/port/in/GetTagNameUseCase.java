package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.domain.TagName;
import java.util.List;

public interface GetTagNameUseCase {
  List<TagName> getTags(Long magazineId);
}
