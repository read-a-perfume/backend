package io.perfume.api.auth.application;

import io.perfume.api.auth.application.exception.NotFoundKeyException;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.auth.application.type.CheckEmailStatus;
import io.perfume.api.auth.domain.AuthenticationKey;
import io.perfume.api.auth.stub.StubAuthenticationKeyQueryRepository;
import io.perfume.api.auth.stub.StubAuthenticationKeyRepository;
import io.perfume.api.auth.stub.StubGenerator;
import io.perfume.api.auth.stub.StubTwoWayEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationKeyServiceTest {

    StubAuthenticationKeyRepository authenticationKeyRepository = new StubAuthenticationKeyRepository();

    StubAuthenticationKeyQueryRepository authenticationKeyQueryRepository = new StubAuthenticationKeyQueryRepository();

    StubTwoWayEncryptor encryptor = new StubTwoWayEncryptor();

    StubGenerator generator = new StubGenerator();

    AuthenticationKeyService authenticationKeyService = new AuthenticationKeyService(authenticationKeyRepository, authenticationKeyQueryRepository, encryptor, generator);

    @BeforeEach
    void setUp() {
        generator.clear();
        authenticationKeyRepository.clear();
        authenticationKeyQueryRepository.clear();
    }

    @Test
    @DisplayName("본인인증키를 검증한다.")
    void testCheckEmailCertificate() {
        // given
        authenticationKeyQueryRepository.add(AuthenticationKey.createAuthenticationKey("code", "key", LocalDateTime.now()));
        CheckEmailCertificateCommand command = new CheckEmailCertificateCommand("key", "code", LocalDateTime.now());

        // when
        CheckEmailCertificateResult result = authenticationKeyService.checkEmailCertificate(command);

        // then
        assertEquals(CheckEmailStatus.MATCH, result.status());
    }

    @Test
    @DisplayName("없는 인증키인 경우 NOT_FOUND를 반환한다.")
    void testCheckEmailCertificateWhenNotFoundKey() {
        // given
        CheckEmailCertificateCommand command = new CheckEmailCertificateCommand("key", "code", LocalDateTime.now());

        // when
        CheckEmailCertificateResult result = authenticationKeyService.checkEmailCertificate(command);

        // then
        assertEquals(CheckEmailStatus.NOT_FOUND, result.status());
    }

    @Test
    @DisplayName("인증키가 만료된 경우 EXPIRED를 반환한다.")
    void testCheckEmailCertificateWhenExpired() {
        // given
        int EXPIRED_MINUTES = 3;
        authenticationKeyQueryRepository.add(AuthenticationKey.createAuthenticationKey("code", "key", LocalDateTime.now().minusMinutes(EXPIRED_MINUTES)));
        CheckEmailCertificateCommand command = new CheckEmailCertificateCommand("key", "code", LocalDateTime.now());

        // when
        CheckEmailCertificateResult result = authenticationKeyService.checkEmailCertificate(command);

        // then
        assertEquals(CheckEmailStatus.EXPIRED, result.status());
    }

    @Test
    @DisplayName("CODE가 불일치할 경우 NOT_MATCH를 반환한다.")
    void testCheckEmailCertificateWhenNotMatchedCode() {
        // given
        authenticationKeyQueryRepository.add(AuthenticationKey.createAuthenticationKey("edoc", "key", LocalDateTime.now()));
        CheckEmailCertificateCommand command = new CheckEmailCertificateCommand("key", "code", LocalDateTime.now());

        // when
        CheckEmailCertificateResult result = authenticationKeyService.checkEmailCertificate(command);

        // then
        assertEquals(CheckEmailStatus.NOT_MATCH, result.status());
    }

    @Test
    void testCreateVerificationCode() {
        // given
        LocalDateTime now = LocalDateTime.now();
        String metadata = "sample metadatakey";
        CreateVerificationCodeCommand command = new CreateVerificationCodeCommand(metadata, now);
        String code = "123456";
        generator.setCode(code);

        // when
        CreateVerificationCodeResult result = authenticationKeyService.createVerificationCode(command);

        // then
        AuthenticationKey storedAuthenticationKey = authenticationKeyRepository.pop();
        assertEquals(result.metadata(), metadata);
        assertEquals(result.code(), storedAuthenticationKey.getCode());
        assertEquals(result.signKey(), storedAuthenticationKey.getKey());
        assertEquals(result.createdAt(), storedAuthenticationKey.getCreatedAt());
    }
}
