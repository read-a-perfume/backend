package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import java.time.LocalDateTime;
import java.util.List;

public record CreateReviewCommand(
    Long perfumeId,
    DayType dayType,
    Strength strength,
    Season season,
    Long duration,
    String feeling,
    String situation,
    List<Long> tags,
    LocalDateTime now
) {
}
