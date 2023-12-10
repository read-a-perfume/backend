package io.perfume.api.review.application.out.comment.dto;

public record ReviewCommentCount(
    long reviewId,
    long count
) {
}
