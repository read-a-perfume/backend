package io.perfume.api.auth.application;

import io.perfume.api.auth.adapter.out.persistence.persistence.AuthenticationKeyQueryRepository;
import io.perfume.api.auth.adapter.out.persistence.persistence.AuthenticationKeyRepository;
import io.perfume.api.auth.application.exception.NotFoundKeyException;
import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.domain.AuthenticationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationKeyService implements CheckEmailCertificateUseCase {

    private final AuthenticationKeyRepository authenticationKeyRepository;

    private final AuthenticationKeyQueryRepository authenticationKeyQueryRepository;

    @Override
    public CheckEmailCertificateResult checkEmailCertificate(CheckEmailCertificateCommand checkEmailCertificateCommand, LocalDateTime now) {
        AuthenticationKey authenticationKey = authenticationKeyQueryRepository
                .findByUserId(checkEmailCertificateCommand.userId())
                .orElseThrow(NotFoundKeyException::new);

        if (authenticationKey.isExpired(now)) {
            return CheckEmailCertificateResult.EXPIRED;
        }

        if (!authenticationKey.matchKey(checkEmailCertificateCommand.key(), now)) {
            return CheckEmailCertificateResult.NOT_MATCH;
        }

        authenticationKeyRepository.save(authenticationKey);
        return CheckEmailCertificateResult.MATCH;
    }
}
