package io.perfume.api.review.application.in.dto;

public record CreateReviewCommentCommand(Long userId, Long reviewId, String content) {}
