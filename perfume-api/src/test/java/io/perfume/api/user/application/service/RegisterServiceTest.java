package io.perfume.api.user.application.service;

import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeCommand;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeResult;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.stub.StubCheckEmailCertificateUseCase;
import io.perfume.api.user.stub.StubCreateVerificationCodeUseCase;
import io.perfume.api.user.stub.StubMailSender;
import io.perfume.api.user.stub.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegisterServiceTest {

    private StubCheckEmailCertificateUseCase checkEmailCertificateUseCase = new StubCheckEmailCertificateUseCase();

    private UserRepository userRepository = new StubUserRepository();

    private StubMailSender stubMailSender = new StubMailSender();

    private StubCreateVerificationCodeUseCase createVerificationCodeUseCase = new StubCreateVerificationCodeUseCase();

    private RegisterService registerService = new RegisterService(userRepository, checkEmailCertificateUseCase, createVerificationCodeUseCase, stubMailSender);

    @BeforeEach
    void setUp() {
        this.checkEmailCertificateUseCase.clear();
        this.stubMailSender.clear();
        this.createVerificationCodeUseCase.clear();
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

    @Test
    @DisplayName("본인인증 이메일을 발송한다.")
    void testSendEmailVerifyCode() {
        // given
        LocalDateTime now = LocalDateTime.now();
        SendVerificationCodeCommand command = new SendVerificationCodeCommand("email", now);
        CreateVerificationCodeResult createVerificationCodeResult = new CreateVerificationCodeResult("sample code", "", "sample key", now);
        createVerificationCodeUseCase.setCreateVerificationCodeResult(createVerificationCodeResult);
        stubMailSender.setSentAt(now);

        // when
        SendVerificationCodeResult result = registerService.sendEmailVerifyCode(command);

        // then
        assertNotNull(result);
        assertEquals(result.key(), "sample key");
        assertEquals(result.sentAt(), now);
    }
}
