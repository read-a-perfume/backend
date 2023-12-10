package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.NotBlank;

public record CheckUsernameRequestDto(@NotBlank String username) {}
