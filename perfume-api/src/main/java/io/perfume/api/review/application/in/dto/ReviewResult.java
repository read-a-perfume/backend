package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;

public record ReviewResult(
    Long id,
    String shortReview,
    String fullReview,
    DayType dayType,
    Strength strength,
    Season season,
    Duration duration,
    Long perfumeId,
    Long authorId
) {

  public static ReviewResult from(Review review) {
    return new ReviewResult(
        review.getId(),
        review.getShortReview(),
        review.getFullReview(),
        review.getDayType(),
        review.getStrength(),
        review.getSeason(),
        review.getDuration(),
        review.getPerfumeId(),
        review.getUserId());
  }
}
