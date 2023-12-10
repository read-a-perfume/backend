package io.perfume.api.review.application.out.tag;

import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;
import java.util.List;

public interface TagQueryRepository {

  List<Tag> findByIds(List<Long> ids);

  List<ReviewTag> findReviewTags(Long reviewId);

  List<ReviewTag> findReviewsTags(List<Long> reviewIds);
}
