package io.perfume.api.user.adapter.in.http.dto;

public record EmailSignUpResponseDto(
    String username,
    String email,
    String name
) {
}
