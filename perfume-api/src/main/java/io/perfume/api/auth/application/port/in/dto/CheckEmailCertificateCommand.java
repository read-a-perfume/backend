package io.perfume.api.auth.application.port.in.dto;

public record CheckEmailCertificateCommand(
        Long userId,
        String key,
        String code
) {

}
