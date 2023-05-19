package io.perfume.api.user.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record EmailVerifyConfirmRequestDto(
        @NotEmpty
        @NotBlank
        String code,
        @NotEmpty
        @NotBlank
        String key
) {
}
