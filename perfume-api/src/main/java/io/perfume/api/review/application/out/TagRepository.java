package io.perfume.api.review.application.out;

import io.perfume.api.review.domain.ReviewTag;

public interface TagRepository {

  ReviewTag save(ReviewTag tags);
}
