package io.perfume.api.auth.application.port.in.dto;

import io.perfume.api.auth.application.type.CheckEmailStatus;

public record CheckEmailCertificateResult(CheckEmailStatus status) {

    public static final CheckEmailCertificateResult MATCH = new CheckEmailCertificateResult(CheckEmailStatus.MATCH);

    public static final CheckEmailCertificateResult EXPIRED = new CheckEmailCertificateResult(CheckEmailStatus.EXPIRED);

    public static final CheckEmailCertificateResult NOT_MATCH = new CheckEmailCertificateResult(CheckEmailStatus.NOT_MATCH);
}
