package io.perfume.api.review.adapter.out.persistence;

import io.perfume.api.review.domain.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

  private final TagMapper tagMapper;

  public ReviewMapper(TagMapper tagMapper) {
    this.tagMapper = tagMapper;
  }

  public Review toDomain(ReviewEntity reviewEntity) {
    return new Review(
        reviewEntity.getId(),
        reviewEntity.getFeeling(),
        reviewEntity.getSituation(),
        reviewEntity.getStrength(),
        reviewEntity.getDuration(),
        reviewEntity.getSeason(),
        reviewEntity.getPerfumeId(),
        reviewEntity.getUserId(),
        reviewEntity.getTags().stream().map(ReviewTagEntity::getTag).map(tagMapper::toDomain)
            .toList(),
        reviewEntity.getCreatedAt(),
        reviewEntity.getUpdatedAt(),
        reviewEntity.getDeletedAt()
    );
  }

  public ReviewEntity toEntity(Review review) {
    var entity = new ReviewEntity(
        review.getId(),
        review.getFeeling(),
        review.getSituation(),
        review.getStrength(),
        review.getDuration(),
        review.getSeason(),
        review.getPerfumeId(),
        review.getUserId(),
        review.getCreatedAt(),
        review.getUpdatedAt(),
        review.getDeletedAt()
    );
    review.getTags().stream().map(tagMapper::toEntity).forEach(entity::addTag);

    return entity;
  }
}
