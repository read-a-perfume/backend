package io.perfume.api.review.application.out.like.dto;

public record ReviewLikeCount(
    long reviewId,
    long count
) {
}
