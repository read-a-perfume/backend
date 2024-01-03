package io.perfume.api.review.application.out.tag;

import io.perfume.api.review.domain.Tag;
import java.util.List;

public interface TagQueryRepository {
  List<Tag> findAll();

  List<Tag> findByIds(List<Long> ids);
}
