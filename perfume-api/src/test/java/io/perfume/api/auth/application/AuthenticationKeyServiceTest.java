package io.perfume.api.auth.application;

import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.auth.domain.AuthenticationKey;
import io.perfume.api.auth.stub.StubAuthenticationKeyQueryRepository;
import io.perfume.api.auth.stub.StubAuthenticationKeyRepository;
import io.perfume.api.auth.stub.StubGenerator;
import io.perfume.api.auth.stub.StubTwoWayEncryptor;
import org.junit.jupiter.api.BeforeEach;
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
