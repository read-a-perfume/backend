package io.perfume.api.perfume.adapter.in.http.dto;

import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.util.Map;

public record PerfumeStatisticResponseDto(Map<Strength, Long> strength, Map<Duration, Long> duration,
                                         Map<Season, Long> season, Map<DayType, Long> dayType,
                                         Map<Sex, Long> sex) {
  public static PerfumeStatisticResponseDto from(ReviewStatisticResult result) {
    return new PerfumeStatisticResponseDto(result.strengthMap(), result.durationMap(), result.seasonMap(), result.dayTypeMap(), result.sexMap());
  }
}
