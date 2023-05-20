package io.perfume.api.user.adapter.in.http.dto;

import java.time.LocalDateTime;

public record SendEmailVerifyCodeResponseDto(String key, LocalDateTime sentAt) {
}
