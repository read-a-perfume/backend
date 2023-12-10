package io.perfume.api.review.adapter.out.persistence.repository.tag;

import io.perfume.api.review.domain.ReviewTag;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ReviewTagMapper {

  public ReviewTag toDomain(@NotNull ReviewTagEntity entity) {
    return new ReviewTag(
        entity.getId().getReviewId(),
        entity.getId().getTagId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt());
  }

  public ReviewTagEntity toEntity(@NotNull ReviewTag domain) {
    return new ReviewTagEntity(
        domain.getReviewId(),
        domain.getTagId(),
        domain.getCreatedAt(),
        domain.getUpdatedAt(),
        null);
  }
}
