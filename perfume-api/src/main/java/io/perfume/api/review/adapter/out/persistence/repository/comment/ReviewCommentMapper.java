package io.perfume.api.review.adapter.out.persistence.repository.comment;

import io.perfume.api.review.domain.ReviewComment;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ReviewCommentMapper {

  public ReviewComment toDomain(@NotNull final ReviewCommentEntity entity) {
    return ReviewComment.builder()
        .id(entity.getId())
        .reviewId(entity.getReviewId())
        .userId(entity.getUserId())
        .content(entity.getContent())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .deletedAt(entity.getDeletedAt())
        .build();
  }

  public ReviewCommentEntity toEntity(@NotNull final ReviewComment domain) {
    return ReviewCommentEntity.builder()
        .id(domain.getId())
        .reviewId(domain.getReviewId())
        .userId(domain.getUserId())
        .content(domain.getContent())
        .createdAt(domain.getCreatedAt())
        .updatedAt(domain.getUpdatedAt())
        .deletedAt(domain.getDeletedAt())
        .build();
  }
}
