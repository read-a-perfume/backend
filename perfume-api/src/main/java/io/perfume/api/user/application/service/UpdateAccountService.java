package io.perfume.api.user.application.service;

import io.perfume.api.user.adapter.out.persistence.exception.UserNotFoundException;
import io.perfume.api.user.application.exception.FailedToUpdateEmailException;
import io.perfume.api.user.application.port.in.UpdateAccountUseCase;
import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAccountService implements UpdateAccountUseCase {
  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final PasswordEncoder passwordEncoder;

  public void updateUserEmail(UpdateEmailCommand updateEmailCommand) {
    if (updateEmailCommand.verified().equals(Boolean.FALSE)) {
      throw new FailedToUpdateEmailException(updateEmailCommand.userId());
    }
    User user = userQueryRepository.loadUser(updateEmailCommand.userId())
        .orElseThrow(() -> new UserNotFoundException(updateEmailCommand.userId()));
    user.updateEmail(updateEmailCommand.email());
    userRepository.save(user);
  }

  public void updateUserPassword(UpdatePasswordCommand updatePasswordCommand) {
    User user = userQueryRepository.loadUser(updatePasswordCommand.userId())
        .orElseThrow(() -> new UserNotFoundException(updatePasswordCommand.userId()));
    user.updatePassword(passwordEncoder, updatePasswordCommand.oldPassword(),
        updatePasswordCommand.newPassword());
    userRepository.save(user);
  }
}
