package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record EmailVerifyConfirmResponseDto(
    @NotEmpty
    @NotBlank
    String email,
    @NotEmpty
    @NotBlank
    LocalDateTime verifiedAt
) {
}
