package io.perfume.api.review.adapter.out.persistence.mapper;

import io.perfume.api.review.adapter.out.persistence.entity.ReviewEntity;
import io.perfume.api.review.domain.Review;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  public Review toDomain(@NotNull ReviewEntity reviewEntity) {
    return new Review(
        reviewEntity.getId(),
        reviewEntity.getFeeling(),
        reviewEntity.getSituation(),
        reviewEntity.getStrength(),
        reviewEntity.getDuration(),
        reviewEntity.getDayType(),
        reviewEntity.getPerfumeId(),
        reviewEntity.getUserId(),
        reviewEntity.getCreatedAt(),
        reviewEntity.getUpdatedAt(),
        reviewEntity.getDeletedAt()
    );
  }

  public ReviewEntity toEntity(@NotNull Review review) {
    return new ReviewEntity(
        review.getId(),
        review.getFeeling(),
        review.getSituation(),
        review.getStrength(),
        review.getDuration(),
        review.getDayType(),
        review.getPerfumeId(),
        review.getUserId(),
        review.getCreatedAt(),
        review.getUpdatedAt(),
        review.getDeletedAt()
    );
  }
}
