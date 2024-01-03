package io.perfume.api.review.application.out.tag;

import io.perfume.api.review.domain.ReviewTag;
import java.util.List;

public interface ReviewTagQueryRepository {

  List<ReviewTag> findReviewTags(Long reviewId);

  List<ReviewTag> findReviewsTags(List<Long> reviewIds);
}
