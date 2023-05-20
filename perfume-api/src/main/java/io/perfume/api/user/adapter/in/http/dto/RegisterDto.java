package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotBlank String username, @NotBlank String password, @Email String email,
                          @NotNull boolean marketingConsent, @NotNull boolean promotionConsent, @NotNull String name) {
}
