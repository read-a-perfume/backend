package io.perfume.api.review.adapter.in.http.dto;

import io.perfume.api.review.domain.ReviewOption;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import java.util.EnumSet;
import java.util.List;

public record GetReviewOptionItemResponseDto(
    List<ReviewOption> strength,
    List<ReviewOption> season,
    List<ReviewOption> duration,
    List<ReviewOption> dayType) {

  public static GetReviewOptionItemResponseDto get() {

    List<ReviewOption> strength =
        EnumSet.allOf(Strength.class).stream()
            .map(enumValue -> new ReviewOption(enumValue.name(), enumValue.getDescription()))
            .toList();

    List<ReviewOption> season =
        EnumSet.allOf(Season.class).stream()
            .map(enumValue -> new ReviewOption(enumValue.name(), enumValue.getDescription()))
            .toList();

    List<ReviewOption> duration =
        EnumSet.allOf(Duration.class).stream()
            .map(enumValue -> new ReviewOption(enumValue.name(), enumValue.getDescription()))
            .toList();

    List<ReviewOption> dayType =
        EnumSet.allOf(DayType.class).stream()
            .map(enumValue -> new ReviewOption(enumValue.name(), enumValue.getDescription()))
            .toList();

    return new GetReviewOptionItemResponseDto(strength, season, duration, dayType);
  }
}
