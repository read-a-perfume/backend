package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewLike extends BaseTimeDomain {

  private final Long id;

  private final long userId;

  private final long reviewId;

  public ReviewLike(Long id, long userId, long reviewId, LocalDateTime createdAt, LocalDateTime updatedAt,
                    LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.userId = userId;
    this.reviewId = reviewId;
  }

  public static ReviewLike create(long userId, long reviewId, LocalDateTime now) {
    return new ReviewLike(null, userId, reviewId, now, now, null);
  }
}
