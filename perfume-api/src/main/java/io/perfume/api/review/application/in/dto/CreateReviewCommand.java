package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import java.util.List;

public record CreateReviewCommand(
    Long perfumeId,
    SEASON season,
    STRENGTH strength,
    Long duration,
    String feeling,
    String situation,
    List<Long> tags
) {
}
