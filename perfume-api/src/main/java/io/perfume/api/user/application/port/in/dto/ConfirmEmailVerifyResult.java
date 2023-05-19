package io.perfume.api.user.application.port.in.dto;

import java.time.LocalDateTime;

public record ConfirmEmailVerifyResult(
        String email,
        LocalDateTime verifiedAt
) {
}
