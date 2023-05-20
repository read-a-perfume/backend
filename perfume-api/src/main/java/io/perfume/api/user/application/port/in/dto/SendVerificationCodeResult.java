package io.perfume.api.user.application.port.in.dto;

import java.time.LocalDateTime;

public record SendVerificationCodeResult(String key, LocalDateTime sentAt) {
}
