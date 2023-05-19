package io.perfume.api.auth.application;

import encryptor.TwoWayEncryptor;
import generator.Generator;
import io.perfume.api.auth.application.port.out.AuthenticationKeyQueryRepository;
import io.perfume.api.auth.application.port.out.AuthenticationKeyRepository;
import io.perfume.api.auth.application.exception.NotFoundKeyException;
import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.CreateVerificationCodeUseCase;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.auth.domain.AuthenticationKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationKeyService implements CheckEmailCertificateUseCase, CreateVerificationCodeUseCase {

    private final AuthenticationKeyRepository authenticationKeyRepository;

    private final AuthenticationKeyQueryRepository authenticationKeyQueryRepository;

    private final TwoWayEncryptor twoWayEncryptor;

    private final Generator generator;

    private final static String SEPERATE_STRING = "::";

    @Override
    @Transactional
    public CheckEmailCertificateResult checkEmailCertificate(CheckEmailCertificateCommand command) {
        AuthenticationKey authenticationKey = authenticationKeyQueryRepository
                .findByKey(command.key())
                .orElseThrow(NotFoundKeyException::new);

        if (authenticationKey.isExpired(command.confirmedAt())) {
            return CheckEmailCertificateResult.EXPIRED;
        }

        if (!authenticationKey.matchKey(command.code(), command.key(), command.confirmedAt())) {
            return CheckEmailCertificateResult.NOT_MATCH;
        }

        authenticationKeyRepository.save(authenticationKey);
        return CheckEmailCertificateResult.MATCH;
    }

    @Override
    public CreateVerificationCodeResult createVerificationCode(CreateVerificationCodeCommand createVerificationCodeCommand) {
        String signKey =  getSignKey(createVerificationCodeCommand.metadata(), createVerificationCodeCommand.now());
        String randomCode = generator.generate(6);

        AuthenticationKey authenticationKey = AuthenticationKey.createAuthenticationKey(
                randomCode,
                signKey,
                createVerificationCodeCommand.now()
        );
        this.authenticationKeyRepository.save(authenticationKey);

        return new CreateVerificationCodeResult(
                randomCode,
                createVerificationCodeCommand.metadata(),
                signKey,
                createVerificationCodeCommand.now());
    }

    private String getSignKey(String metadata, LocalDateTime now) {
        String sb = metadata + SEPERATE_STRING + now;
        try {
            return twoWayEncryptor.encrypt(sb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String extractEmailFromSignKey(String key) {
        try {
            String decrypt = twoWayEncryptor.decrypt(key);
            return decrypt.split(SEPERATE_STRING)[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
