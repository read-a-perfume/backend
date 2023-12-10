package io.perfume.api.review.adapter.out.persistence.repository.review;

import io.perfume.api.review.domain.Review;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public Review toDomain(@NotNull ReviewEntity reviewEntity) {
    return new Review(
        reviewEntity.getId(),
        reviewEntity.getFullReview(),
        reviewEntity.getShortReview(),
        reviewEntity.getStrength(),
        reviewEntity.getDuration(),
        reviewEntity.getDayType(),
        reviewEntity.getPerfumeId(),
        reviewEntity.getUserId(),
        reviewEntity.getSeason(),
        reviewEntity.getCreatedAt(),
        reviewEntity.getUpdatedAt(),
        reviewEntity.getDeletedAt());
  }

  public ReviewEntity toEntity(@NotNull Review review) {
    return new ReviewEntity(
        review.getId(),
        review.getFullReview(),
        review.getShortReview(),
        review.getStrength(),
        review.getDuration(),
        review.getDayType(),
        review.getPerfumeId(),
        review.getUserId(),
        review.getSeason(),
        review.getCreatedAt(),
        review.getUpdatedAt(),
        review.getDeletedAt());
  }
}
