package io.perfume.api.review.application.in.dto;

import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
import java.util.List;

public record CreateReviewCommentCommand(
    Long userId,
    Long reviewId,
    String content
) {
}
