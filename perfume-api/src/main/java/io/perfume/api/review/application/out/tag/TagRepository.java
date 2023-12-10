package io.perfume.api.review.application.out.tag;

import io.perfume.api.review.domain.ReviewTag;
import java.util.List;

public interface TagRepository {

  ReviewTag save(ReviewTag tags);

  List<ReviewTag> saveAll(List<ReviewTag> tags);
}
