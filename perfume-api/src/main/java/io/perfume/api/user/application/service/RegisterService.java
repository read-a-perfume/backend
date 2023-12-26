package io.perfume.api.user.application.service;

import io.perfume.api.auth.application.port.in.CheckEmailCertificateUseCase;
import io.perfume.api.auth.application.port.in.CreateVerificationCodeUseCase;
import io.perfume.api.user.application.exception.FailedRegisterException;
import io.perfume.api.user.application.exception.UserConflictException;
import io.perfume.api.user.application.port.in.CreateUserUseCase;
import io.perfume.api.user.application.port.in.dto.ConfirmEmailVerifyResult;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeCommand;
import io.perfume.api.user.application.port.in.dto.SendVerificationCodeResult;
import io.perfume.api.user.application.port.in.dto.SignUpGeneralUserCommand;
import io.perfume.api.user.application.port.in.dto.SignUpSocialUserCommand;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.EmailCodeRepository;
import io.perfume.api.user.application.port.out.SocialAccountRepository;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mailer.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService implements CreateUserUseCase {

  private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
  private static final Duration duration = Duration.ofMinutes(10);

  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final SocialAccountRepository oauthRepository;
  private final EmailCodeRepository emailCodeRepository;

  private final CheckEmailCertificateUseCase checkEmailCertificateUseCase;
  private final CreateVerificationCodeUseCase createVerificationCodeUseCase;
  private final MailSender mailSender;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserResult signUpGeneralUserByEmail(SignUpGeneralUserCommand command) {
    if (Boolean.TRUE.equals(
        userRepository.existByUsernameOrEmail(command.username(), command.email()))) {
      throw new UserConflictException(command.username(), command.email());
    }

    User user =
        User.generalUserJoin(
            command.username(),
            command.email(),
            passwordEncoder.encode(command.password()),
            command.marketingConsent(),
            command.promotionConsent());

    return toDto(userRepository.save(user).orElseThrow(FailedRegisterException::new));
  }

  @Override
  public UserResult signUpSocialUser(SignUpSocialUserCommand command, LocalDateTime now) {
    SocialAccount socialAccount =
        SocialAccount.createGoogleSocialAccount(command.identifier(), now);

    User user = getUserByEmailOrCreateNew(command, now);
    socialAccount.connect(user);

    return oauthRepository
        .save(socialAccount)
        .map(this::toSocialAccountDto)
        .orElseThrow(FailedRegisterException::new);
  }

  @Transactional(readOnly = true)
  @Override
  public boolean validDuplicateUsername(String username) {
    try {
      return userQueryRepository.findByUsername(username).isEmpty();
    } catch (Exception e) {
      return true;
    }
  }

  @Override
  public ConfirmEmailVerifyResult confirmEmailVerify(String email, String code) {
    emailCodeRepository.verify(email, code);
    LocalDateTime now = LocalDateTime.now();
    return new ConfirmEmailVerifyResult(email, now);
  }

  @Override
  public SendVerificationCodeResult sendEmailVerifyCode(SendVerificationCodeCommand command) {
    // code 생성을 UUID로 대체하여 주석 처리했습니다.
    //    CreateVerificationCodeCommand createVerificationCodeCommand =
    //        new CreateVerificationCodeCommand(command.email(), command.now());
    //    CreateVerificationCodeResult result =
    //        createVerificationCodeUseCase.createVerificationCode(createVerificationCodeCommand);
    String code = UUID.randomUUID().toString().substring(0, 6);

    LocalDateTime sentAt = mailSender.send(command.email(), "Read A Perfume 이메일을 인증해 주세요.", code);

    logger.info(
        "sendEmailVerifyCode email = {}, code = {}, now = {}", command.email(), code, sentAt);

    emailCodeRepository.save(command.email(), code, duration);

    return new SendVerificationCodeResult(code, sentAt);
  }

  private User getUserByEmailOrCreateNew(SignUpSocialUserCommand command, LocalDateTime now) {
    return userQueryRepository
        .findOneByEmail(command.email())
        .orElseGet(
            () -> {
              User user =
                  User.createSocialUser(
                      command.username(), command.email(), command.password(), now);

              return userRepository.save(user).orElseThrow();
            });
  }

  private UserResult toDto(User user) {
    return new UserResult(
        user.getId(),
        user.getUsername(),
        user.getThumbnailId(),
        user.getEmail(),
        user.getCreatedAt());
  }

  private UserResult toSocialAccountDto(SocialAccount socialAccount) {
    return toDto(socialAccount.getUser());
  }
}
