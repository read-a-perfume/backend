package io.perfume.api.auth.application.port.in.dto;

public record CheckEmailCertificateCommand(
        String key,
        String code
) {

}
