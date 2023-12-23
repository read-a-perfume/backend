package io.perfume.api.review.domain;

import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.util.Map;

public record ReviewFeatureCount(
    Map<Strength, Long> strengthMap,
    Map<Duration, Long> durationMap,
    Map<Season, Long> seasonMap,
    Map<DayType, Long> dayTypeMap,
    Map<Sex, Long> sexMap,
    Long totalReviews) {

  public static ReviewFeatureCount EMPTY =
      new ReviewFeatureCount(Map.of(), Map.of(), Map.of(), Map.of(), Map.of(), 0L);
}
