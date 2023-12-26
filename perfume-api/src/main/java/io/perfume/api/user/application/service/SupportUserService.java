package io.perfume.api.user.application.service;

import encryptor.OneWayEncryptor;
import io.perfume.api.user.application.exception.FailedToLeaveException;
import io.perfume.api.user.application.exception.NotFoundUserException;
import io.perfume.api.user.application.exception.UserNotFoundException;
import io.perfume.api.user.application.port.in.FindEncryptedUsernameUseCase;
import io.perfume.api.user.application.port.in.LeaveUserUseCase;
import io.perfume.api.user.application.port.in.SendResetPasswordMailUseCase;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import jwt.JsonWebTokenGenerator;
import lombok.RequiredArgsConstructor;
import mailer.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupportUserService
    implements SendResetPasswordMailUseCase, FindEncryptedUsernameUseCase, LeaveUserUseCase {

  private final UserQueryRepository userQueryRepository;

  private final UserRepository userRepository;
  private final MailSender mailSender;
  private final OneWayEncryptor oneWayEncryptor;

  private final JsonWebTokenGenerator jsonWebTokenGenerator;

  /** TODO 비밀번호 변경파트 고민하기 1. 이메일과 아이디를 알면 누구나 다른 사용자의 암호를 수정할 수 있음 2. "새로운 패스워드 할당" 이메일 템플릿 정하기 */
  @Override
  @Transactional
  public void sendNewPasswordToEmail(String email, String username) {
    User user =
        userQueryRepository
            .findOneByEmailAndUsername(email, username)
            .orElseThrow(NotFoundUserException::new);

    LocalDateTime now = LocalDateTime.now();

    user.resetPasswordFromForgotten(now, oneWayEncryptor);

    userRepository.save(user);

    mailSender.send(
        email,
        "[read a perfume] " + user.getUsername() + "님의 새로운 암호입니다.",
        "[read a perfume] 사용자의 암호 : " + user.getPassword());
  }

  @Override
  public String findEncryptedUsername(String email) {
    User user = userQueryRepository.findOneByEmail(email).orElseThrow(NotFoundUserException::new);
    return user.getEncryptedUsername();
  }

  @Override
  public void leave(Long userId) {
    try {
      User user = userQueryRepository.loadUser(userId)
              .orElseThrow(() -> new UserNotFoundException(userId));
      LocalDateTime now = LocalDateTime.now();
      user.softDelete(now);
      userRepository.save(user);
    } catch (NotFoundUserException e) {
      FailedToLeaveException failedLeaveException =
          new FailedToLeaveException("회원탈퇴 실패", "user id : " + userId + ", 회원탈퇴 실패");
      failedLeaveException.initCause(e);
      throw failedLeaveException;
    }
  }
}
