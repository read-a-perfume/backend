package io.perfume.api.auth.application.port.in.dto;

import java.time.LocalDateTime;

public record CheckEmailCertificateCommand(
    String key,
    String code,
    LocalDateTime confirmedAt
) {
}
