package io.perfume.api.review.adapter.in.http.dto;

public record GetReviewOptionItemRequestDto(ReviewOptionType type) {

  public enum ReviewOptionType {
    STRENGTH,
    SEASON,
    DURATION,
    DAY_TYPE
  }
}
