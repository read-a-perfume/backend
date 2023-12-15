package io.perfume.api.user.application.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;
import io.perfume.api.user.application.port.in.dto.UpdateProfileCommand;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import io.perfume.api.user.stub.StubEncryptor;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UpdateAccountServiceTest {

  @InjectMocks private UpdateAccountService updateAccountService;
  @Mock private UserRepository userRepository;
  @Mock private UserQueryRepository userQueryRepository;

  @Spy private PasswordEncoder passwordEncoder = new StubEncryptor();

  @Test
  void updateEmail() {
    // given
    User user = User.builder().id(1L).username("username").email("old@email.com").build();
    given(userQueryRepository.loadUser(anyLong())).willReturn(Optional.ofNullable(user));

    UpdateEmailCommand updateEmailCommand = new UpdateEmailCommand(1L, true, "new@email.com");
    User updatedUser = User.builder().id(1L).username("username").email("new@email.com").build();
    // when
    updateAccountService.updateUserEmail(updateEmailCommand);

    // then
    verify(userRepository).save(updatedUser);
  }

  @Test
  void updatePassword() {
    // given
    User user =
        User.builder()
            .id(1L)
            .username("username")
            .password(passwordEncoder.encode("oldPassword"))
            .build();
    given(userQueryRepository.loadUser(anyLong())).willReturn(Optional.ofNullable(user));
    User updatedUser = User.builder().id(1L).username("username").password("newPassword").build();

    // when
    UpdatePasswordCommand updatePasswordCommand =
        new UpdatePasswordCommand(1L, passwordEncoder.encode("oldPassword"), "newPassword");
    updateAccountService.updateUserPassword(updatePasswordCommand);

    // then
    verify(userRepository).save(updatedUser);
  }

  @Test
  void updateProfile() {
    // given
    User user = User.builder().id(1L).username("username").build();
    given(userQueryRepository.loadUser(anyLong())).willReturn(Optional.ofNullable(user));
    User updatedUser = User.builder().id(1L).username("username").bio("향수를 좋아하는 1인").build();
    // when
    UpdateProfileCommand updateProfileCommand =
        new UpdateProfileCommand(1L, "향수를 좋아하는 1인", null, null);
    updateAccountService.updateUserProfile(updateProfileCommand);

    // then
    verify(userRepository).save(updatedUser);
  }
}
