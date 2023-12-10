package io.perfume.api.fixture;

import io.perfume.api.review.adapter.out.persistence.repository.thumbnail.ReviewThumbnailEntity;
import io.perfume.api.review.adapter.out.persistence.repository.thumbnail.ReviewThumbnailMapper;
import io.perfume.api.review.domain.ReviewThumbnail;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReviewThumbnailFixture {

  private final ReviewThumbnailMapper mapper;

  public ReviewThumbnailFixture(final ReviewThumbnailMapper mapper) {
    this.mapper = mapper;
  }

  public ReviewThumbnail createReviewThumbnail(final long reviewId, final long thumbnailId,
                                               final LocalDateTime now) {
    final ReviewThumbnailEntity entity =
        new ReviewThumbnailEntity(reviewId, thumbnailId, now, now, null);
    return mapper.toDomain(entity);
  }
}
