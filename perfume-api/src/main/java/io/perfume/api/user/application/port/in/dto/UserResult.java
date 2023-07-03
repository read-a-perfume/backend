package io.perfume.api.user.application.port.in.dto;

import java.time.LocalDateTime;

public record UserResult(Long id, String username, String email, String name, LocalDateTime createdAt) {
}
