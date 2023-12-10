package io.perfume.api.review.adapter.out.persistence.repository.thumbnail;

import io.perfume.api.review.domain.ReviewThumbnail;
import org.springframework.stereotype.Component;

@Component
public class ReviewThumbnailMapper {

  public ReviewThumbnail toDomain(ReviewThumbnailEntity entity) {
    return new ReviewThumbnail(
        entity.getId().getReviewId(),
        entity.getId().getThumbnailId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt());
  }

  public ReviewThumbnailEntity toEntity(ReviewThumbnail domain) {
    return new ReviewThumbnailEntity(
        domain.getReviewId(),
        domain.getThumbnailId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        domain.getDeletedAt());
  }
}
