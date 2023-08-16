package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequestDto(@NotBlank String oldPassword, @NotBlank String newPassword) {
}
