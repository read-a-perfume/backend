package io.perfume.api.user.application.port.in.dto;

import java.time.LocalDateTime;

public record SendVerificationCodeCommand(String email, LocalDateTime now) {
}
