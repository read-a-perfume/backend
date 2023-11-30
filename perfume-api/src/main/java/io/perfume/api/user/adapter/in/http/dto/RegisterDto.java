package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
    @NotBlank String username,
    @NotBlank String password,
    @Email
    String email,
    boolean marketingConsent,
    boolean promotionConsent
) {
}
