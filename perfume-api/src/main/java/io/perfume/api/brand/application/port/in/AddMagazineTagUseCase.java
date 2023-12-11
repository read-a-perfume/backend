package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.domain.TagName;
import java.time.LocalDateTime;
import java.util.List;

public interface AddMagazineTagUseCase {

  List<TagName> addTags(Long magazineId, List<String> tags, LocalDateTime now);
}
