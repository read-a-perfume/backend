package io.perfume.api.user.infrastructure.api.dto;

import java.time.LocalDateTime;

public record SendEmailVerifyCodeResponseDto(String key, LocalDateTime sentAt) {
}
