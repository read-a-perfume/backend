package io.perfume.api.review.application.out;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewTagEntity;
import io.perfume.api.review.domain.ReviewTag;
import java.util.List;

public interface TagRepository {

  ReviewTag save(ReviewTag tags);
}
