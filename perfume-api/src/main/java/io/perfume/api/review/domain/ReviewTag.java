package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewTag extends BaseTimeDomain {

  private final Long reviewId;

  private final Long tagId;

  public ReviewTag(
      Long reviewId,
      Long tagId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.reviewId = reviewId;
    this.tagId = tagId;
  }

  public static ReviewTag create(Long reviewId, Long tagId, LocalDateTime now) {
    return new ReviewTag(reviewId, tagId, now, now, null);
  }
}
