package io.perfume.api.review.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewLike {

  private final Long id;

  private final long userId;

  private final long reviewId;

  private final LocalDateTime createdAt;

  private final LocalDateTime updatedAt;

  private final LocalDateTime deletedAt;

  public ReviewLike(Long id, long userId, long reviewId, LocalDateTime createdAt, LocalDateTime updatedAt,
                    LocalDateTime deletedAt) {
    this.id = id;
    this.userId = userId;
    this.reviewId = reviewId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public static ReviewLike create(long userId, long reviewId, LocalDateTime now) {
    return new ReviewLike(null, userId, reviewId, now, now, null);
  }
}
