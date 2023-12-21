package io.perfume.api.review.application.facade.dto;

public record ReviewCommentEvent(Long reviewId, Long receiveUserId) {}
