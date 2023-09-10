package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.ReviewTag;
import io.perfume.api.review.domain.Tag;

public record ReviewTagResult(
    Long id,
    String name,
    Long reviewId
) {

  public static ReviewTagResult from(Tag tag, ReviewTag reviewTag) {
    return new ReviewTagResult(tag.getId(), tag.getName(), reviewTag.getReviewId());
  }
}
