package io.perfume.api.file.application.port.in.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SaveFileResult(@NotNull String url, @NotNull Long userId, @NotNull Long fileId, LocalDateTime now) {
}
