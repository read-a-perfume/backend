package io.perfume.api.perfume.adapter.in.http.dto;

public record FavoritePerfumeResponseDto(
    Long userId,
    Long perfumeId
) {
}
