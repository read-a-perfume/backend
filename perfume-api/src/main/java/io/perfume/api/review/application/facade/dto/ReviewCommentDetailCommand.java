package io.perfume.api.review.application.facade.dto;

public record ReviewCommentDetailCommand(
    long reviewId,
    long size,
    Long after,
    Long before
) {
}
