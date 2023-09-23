package io.perfume.api.review.adapter.in.http.dto;

public record ReviewUser(
    Long id,
    String username,
    String thumbnailUrl
) {
}
