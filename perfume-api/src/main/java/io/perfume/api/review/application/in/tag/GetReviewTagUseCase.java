package io.perfume.api.review.application.in.tag;

import io.perfume.api.review.application.in.dto.ReviewTagResult;
import java.util.List;

public interface GetReviewTagUseCase {

  List<ReviewTagResult> getReviewTags(Long reviewId);

  List<ReviewTagResult> getReviewsTags(List<Long> reviewIds);
}
