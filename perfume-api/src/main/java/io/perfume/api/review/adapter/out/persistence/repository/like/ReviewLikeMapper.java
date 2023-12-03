package io.perfume.api.review.adapter.out.persistence.repository.like;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewLikeEntity;
import io.perfume.api.review.domain.ReviewLike;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ReviewLikeMapper {

  public ReviewLike toDomain(@NotNull ReviewLikeEntity entity) {
    return new ReviewLike(
        entity.getId(),
        entity.getUserId(),
        entity.getReviewId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getDeletedAt());
  }

  public ReviewLikeEntity toEntity(@NotNull ReviewLike review) {
    return new ReviewLikeEntity(
        review.getId(),
        review.getUserId(),
        review.getReviewId(),
        review.getCreatedAt(),
        review.getUpdatedAt(),
        review.getDeletedAt());
  }
}
