package io.perfume.api.user.application.service;

import io.perfume.api.user.application.exception.NotFoundUserException;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mailer.MailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

  private final UserQueryRepository userQueryRepository;

  private final MailSender mailSender;

  @Override
  public Optional<UserResult> findOneByEmail(String email) {
    return userQueryRepository.findOneByEmail(email).map(this::toDto);
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getName(),
        user.getCreatedAt());
  }
  @Override
  public void sendPasswordToEmail(String email, String username) {

    User user = userQueryRepository.findOneByEmailAndUsername(email, username).orElseThrow(NotFoundUserException::new);
    mailSender.send(email, "[read a perfume] 비밀번호 찾기 ", "[read a perfume] 사용자의 암호 : " + user.getPassword());
  }

  @Override
  public String getEncryptedUsernameByEmail(String email) {

    User user = userQueryRepository.findOneByEmail(email).orElseThrow(NotFoundUserException::new);

    return user.getEncryptedUsernameByEmail();
  }

}
