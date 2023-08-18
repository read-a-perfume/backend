package io.perfume.api.review.application.out;

import io.perfume.api.review.domain.Tag;
import java.util.List;

public interface TagQueryRepository {

  List<Tag> findByIds(List<Long> ids);
}
