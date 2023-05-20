package io.perfume.api.auth.application.port.in;

import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;

import java.time.LocalDateTime;

public interface CheckEmailCertificateUseCase {

    CheckEmailCertificateResult checkEmailCertificate(CheckEmailCertificateCommand checkEmailCertificateCommand);
}
