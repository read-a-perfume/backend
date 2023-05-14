package io.perfume.api.user.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotBlank String username, @NotBlank String password, @NotBlank String email,
                          @NotNull boolean marketingConsent, @NotNull boolean promotionConsent, @NotNull String name) {
}
