package io.perfume.api.user.application.service;

import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.CreateVerificationCodeUseCase;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateCommand;
import io.perfume.api.auth.application.port.in.dto.CheckEmailCertificateResult;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeCommand;
import io.perfume.api.auth.application.port.in.dto.CreateVerificationCodeResult;
import io.perfume.api.user.application.exception.FailedRegisterException;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeCommand;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeResult;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import mailer.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterService implements CreateUserUseCase {

  private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final CheckEmailCertificateUseCase checkEmailCertificateUseCase;
  private final CreateVerificationCodeUseCase createVerificationCodeUseCase;
  private final MailSender mailSender;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserResult signUpGeneralUserByEmail(SignUpGeneralUserCommand command) {
    User user = User.generalUserJoin(
        command.username(),
        command.email(),
        passwordEncoder.encode(command.password()),
        command.name(),
        command.marketingConsent(),
        command.promotionConsent());

    return toDto(userRepository.save(user).orElseThrow(FailedRegisterException::new));
  }

  public boolean validDuplicateUsername(String username) {
    try {
      return userQueryRepository.findByUsername(username).isEmpty();
    } catch (Exception e) {
      return true;
    }
  }

  @Transactional
  public ConfirmEmailVerifyResult confirmEmailVerify(String code, String key, LocalDateTime now) {
    logger.info("confirmEmailVerify code = {}, key = {}, now = {}", code, key, now);

    CheckEmailCertificateCommand command = new CheckEmailCertificateCommand(code, key, now);
    CheckEmailCertificateResult result =
        checkEmailCertificateUseCase.checkEmailCertificate(command);

    return new ConfirmEmailVerifyResult(result.email(), now);
  }

  @Transactional
  public SendVerificationCodeResult sendEmailVerifyCode(SendVerificationCodeCommand command) {
    CreateVerificationCodeCommand createVerificationCodeCommand =
        new CreateVerificationCodeCommand(command.email(), command.now());
    CreateVerificationCodeResult result =
        createVerificationCodeUseCase.createVerificationCode(createVerificationCodeCommand);

    LocalDateTime sentAt = mailSender.send(command.email(), "이메일 인증을 완료해주세요.", result.code());

    logger.info("sendEmailVerifyCode email = {}, code = {}, key = {}, now = {}", command.email(),
        result.code(), result.signKey(), sentAt);

    return new SendVerificationCodeResult(result.signKey(), sentAt);
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getName(),
        user.getCreatedAt());
  }
}
