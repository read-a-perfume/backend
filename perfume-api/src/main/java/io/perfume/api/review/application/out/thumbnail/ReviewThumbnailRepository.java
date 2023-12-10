package io.perfume.api.review.application.out.thumbnail;

import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.ReviewThumbnail;
import java.util.List;

public interface ReviewThumbnailRepository {

  ReviewThumbnail save(ReviewThumbnail thumbnail);

  List<ReviewThumbnail> saveAll(List<ReviewThumbnail> thumbnails);
}
