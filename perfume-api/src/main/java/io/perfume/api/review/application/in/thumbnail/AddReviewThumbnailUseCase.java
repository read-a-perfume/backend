package io.perfume.api.review.application.in.thumbnail;

import io.perfume.api.review.domain.ReviewThumbnail;
import java.time.LocalDateTime;
import java.util.List;

public interface AddReviewThumbnailUseCase {

  List<ReviewThumbnail> addThumbnails(final Long reviewId, final List<Long> thumbnailIds, final LocalDateTime now);
}
