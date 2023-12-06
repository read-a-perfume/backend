package io.perfume.api.review.domain;

import io.perfume.api.base.BaseTimeDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewThumbnail extends BaseTimeDomain {

  private final Long reviewId;

  private final Long thumbnailId;

  public ReviewThumbnail(Long reviewId, Long thumbnailId, LocalDateTime createdAt, LocalDateTime updatedAt,
                         LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);

    this.reviewId = reviewId;
    this.thumbnailId = thumbnailId;
  }

  public static ReviewTag create(Long reviewId, Long tagId, LocalDateTime now) {
    return new ReviewTag(reviewId, tagId, now, now, null);
  }
}
