package io.perfume.api.review.application.in;

import io.perfume.api.review.domain.Tag;
import java.util.List;

public interface AddReviewTagUseCase {

  List<Tag> addTags(Long reviewId, List<Long> tagIds);
}
