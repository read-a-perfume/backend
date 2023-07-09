package io.perfume.api.auth.application.port.in.dto;

import java.time.LocalDateTime;

public record CreateVerificationCodeResult(String code, String metadata, String signKey,
                                           LocalDateTime createdAt) {
}
