package io.perfume.api.user.adapter.in.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateEmailRequestDto(@NotNull Boolean verified, @NotNull @Email String email) {}
