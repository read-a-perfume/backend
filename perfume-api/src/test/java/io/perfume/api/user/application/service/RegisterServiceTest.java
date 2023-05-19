package io.perfume.api.user.application.service;

import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.stub.StubCheckEmailCertificateUseCase;
import io.perfume.api.user.stub.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterServiceTest {

    private StubCheckEmailCertificateUseCase checkEmailCertificateUseCase = new StubCheckEmailCertificateUseCase();

    private UserRepository userRepository = new StubUserRepository();

    private RegisterService registerService = new RegisterService(userRepository, checkEmailCertificateUseCase);

    @BeforeEach
    void setUp() {
        this.checkEmailCertificateUseCase.clearn();
    }

    @Test
    @DisplayName("발급받은 본인인증 코드를 검증한다.")
    void testConfirmEmailVerify() {
        // given
        String code = "code";
        String key = "key";
        LocalDateTime now = LocalDateTime.now();
        this.checkEmailCertificateUseCase.add(CheckEmailCertificateResult.MATCH);

        // when
        ConfirmEmailVerifyResult result = registerService.confirmEmailVerify(code, key, now);

        // then
        assertEquals(result.email(), "");
    }
}
