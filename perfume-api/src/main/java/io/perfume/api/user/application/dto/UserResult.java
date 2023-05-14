package io.perfume.api.user.application.dto;

import java.time.LocalDateTime;

public record UserResult(String username, String email, String name, LocalDateTime createdAt) {
}
