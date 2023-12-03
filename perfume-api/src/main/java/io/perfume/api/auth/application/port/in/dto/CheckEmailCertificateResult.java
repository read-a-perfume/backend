package io.perfume.api.auth.application.port.in.dto;

import io.perfume.api.auth.application.type.CheckEmailStatus;

public record CheckEmailCertificateResult(CheckEmailStatus status, String email) {}
