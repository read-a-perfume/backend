package io.perfume.api.auth.application.service;

import encryptor.TwoWayEncryptor;
import generator.Generator;
import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.CreateVerificationCodeUseCase;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.auth.application.port.out.AuthenticationKeyQueryRepository;
import io.perfume.api.auth.application.port.out.AuthenticationKeyRepository;
import io.perfume.api.auth.application.type.CheckEmailStatus;
import io.perfume.api.auth.domain.AuthenticationKey;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationKeyService
    implements CheckEmailCertificateUseCase, CreateVerificationCodeUseCase {

  private static final String SEPERATE_STRING = "::";
  private final AuthenticationKeyRepository authenticationKeyRepository;
  private final AuthenticationKeyQueryRepository authenticationKeyQueryRepository;
  private final TwoWayEncryptor twoWayEncryptor;
  private final Generator generator;

  @Override
  @Transactional
  public CheckEmailCertificateResult checkEmailCertificate(CheckEmailCertificateCommand command) {
    Optional<AuthenticationKey> authenticationKey =
        authenticationKeyQueryRepository.findByKey(command.key());
    if (authenticationKey.isEmpty()) {
      return new CheckEmailCertificateResult(CheckEmailStatus.NOT_FOUND, null);
    }

    AuthenticationKey unwraapedAuthenticationKey = authenticationKey.get();
    String email = this.extractEmailFromSignKey(unwraapedAuthenticationKey.getKey());
    if (unwraapedAuthenticationKey.isExpired(command.confirmedAt())) {
      return new CheckEmailCertificateResult(CheckEmailStatus.EXPIRED, email);
    }
    if (!unwraapedAuthenticationKey.matchKey(
        command.code(), command.key(), command.confirmedAt())) {
      return new CheckEmailCertificateResult(CheckEmailStatus.NOT_MATCH, email);
    }
    authenticationKeyRepository.save(unwraapedAuthenticationKey);
    return new CheckEmailCertificateResult(CheckEmailStatus.MATCH, email);
  }

  @Override
  public CreateVerificationCodeResult createVerificationCode(
      CreateVerificationCodeCommand createVerificationCodeCommand) {
    String signKey =
        getSignKey(createVerificationCodeCommand.metadata(), createVerificationCodeCommand.now());
    String randomCode = generator.generate(6);

    AuthenticationKey authenticationKey =
        AuthenticationKey.createAuthenticationKey(
            randomCode, signKey, createVerificationCodeCommand.now());
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
