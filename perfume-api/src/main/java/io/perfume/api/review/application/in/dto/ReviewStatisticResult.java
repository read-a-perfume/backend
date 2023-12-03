package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import java.util.Map;

public record ReviewStatisticResult(Map<Strength, Long> strengthMap, Map<Duration, Long> durationMap, Map<Season, Long> seasonMap,
                                    Map<DayType, Long> dayTypeMap, Map<Sex, Long> sexMap) {
}
