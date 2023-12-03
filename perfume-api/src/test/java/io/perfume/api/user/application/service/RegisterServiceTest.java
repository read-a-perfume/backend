package io.perfume.api.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.auth.application.type.CheckEmailStatus;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeCommand;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeResult;
import io.perfume.api.user.application.port.in.dto.SignUpSocialUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.SocialProvider;
import io.perfume.api.user.stub.StubCheckEmailCertificateUseCase;
import io.perfume.api.user.stub.StubCreateVerificationCodeUseCase;
import io.perfume.api.user.stub.StubEncryptor;
import io.perfume.api.user.stub.StubMailSender;
import io.perfume.api.user.stub.StubSocialAccountRepository;
import io.perfume.api.user.stub.StubUserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class RegisterServiceTest {

  private StubCheckEmailCertificateUseCase checkEmailCertificateUseCase =
      new StubCheckEmailCertificateUseCase();

  private UserRepository userRepository = new StubUserRepository();

  private UserQueryRepository userQueryRepository = new StubUserRepository();

  private StubMailSender stubMailSender = new StubMailSender();

  private PasswordEncoder passwordEncoder = new StubEncryptor();

  private StubCreateVerificationCodeUseCase createVerificationCodeUseCase =
      new StubCreateVerificationCodeUseCase();

  private StubSocialAccountRepository socialAccountRepository = new StubSocialAccountRepository();

  private RegisterService registerService =
      new RegisterService(
          userRepository,
          userQueryRepository,
          socialAccountRepository,
          checkEmailCertificateUseCase,
          createVerificationCodeUseCase,
          stubMailSender,
          passwordEncoder);

  @BeforeEach
  void setUp() {
    this.checkEmailCertificateUseCase.clear();
    this.stubMailSender.clear();
    this.createVerificationCodeUseCase.clear();
  }

  @Test
  @DisplayName("소셜 계정 정보로 회원가입한다.")
  void testSignUpSocialAccount() {
    // given
    LocalDateTime now = LocalDateTime.now();
    String email = "name@mail.com";
    String username = "testusername";
    String name = "testname";
    String password = "testpassword";
    SignUpSocialUserCommand command =
        new SignUpSocialUserCommand(
            "abcd12341234", email, username, password, name, SocialProvider.GOOGLE);

    // when
    UserResult result = registerService.signUpSocialUser(command, now);

    // then
    assertThat(result).isNotNull();
    assertThat(result.email()).isEqualTo(email);
    assertThat(result.username()).isEqualTo(username);
  }

  @Test
  @DisplayName("발급받은 본인인증 코드를 검증한다.")
  void testConfirmEmailVerify() {
    // given
    String code = "code";
    String key = "key";
    LocalDateTime now = LocalDateTime.now();
    this.checkEmailCertificateUseCase.add(
        new CheckEmailCertificateResult(CheckEmailStatus.MATCH, "sample@mail.com"));

    // when
    ConfirmEmailVerifyResult result = registerService.confirmEmailVerify(code, key, now);

    // then
    assertThat(result.email()).isEqualTo("sample@mail.com");
  }

  @Test
  @DisplayName("본인인증 이메일을 발송한다.")
  void testSendEmailVerifyCode() {
    // given
    LocalDateTime now = LocalDateTime.now();
    SendVerificationCodeCommand command = new SendVerificationCodeCommand("email", now);
    CreateVerificationCodeResult createVerificationCodeResult =
        new CreateVerificationCodeResult("sample code", "", "sample key", now);
    createVerificationCodeUseCase.setCreateVerificationCodeResult(createVerificationCodeResult);
    stubMailSender.setSentAt(now);

    // when
    SendVerificationCodeResult result = registerService.sendEmailVerifyCode(command);

    // then
    assertThat(result).isNotNull();
    assertThat(result.key()).isEqualTo("sample key");
    assertThat(result.sentAt()).isEqualTo(now);
  }
}
