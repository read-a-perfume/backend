package io.perfume.api.user.adapter.in.http;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.user.adapter.in.http.dto.LeaveUserDto;
import io.perfume.api.user.adapter.in.http.dto.UserProfileDto;
import io.perfume.api.user.adapter.in.http.dto.UpdateEmailRequestDto;
import io.perfume.api.user.adapter.in.http.dto.UpdatePasswordRequestDto;
import io.perfume.api.user.adapter.in.http.exception.UserNotAuthenticatedException;
import io.perfume.api.user.application.port.in.FindEncryptedUsernameUseCase;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.LeaveUserUseCase;
import io.perfume.api.user.application.port.in.SendResetPasswordMailUseCase;
import io.perfume.api.user.application.port.in.UpdateAccountUseCase;
import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;
import io.perfume.api.user.application.port.in.dto.UserProfileResult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserSupportController {

  private final FindEncryptedUsernameUseCase findEncryptedUsernameUseCase;
  private final FindUserUseCase findUserUseCase;
  private final FindFileUseCase findFileUseCase;
  private final SendResetPasswordMailUseCase resetPasswordUserCase;
  private final UpdateAccountUseCase updateAccountUseCase;

  private final LeaveUserUseCase leaveUserUseCase;

  /**
   * TODO OAUTH 가입자의 경우 uesrname도 같이 넣는 흐름이 이상하다.
   */
  @GetMapping("/email/reset-password")
  public ResponseEntity<Object> sendPasswordToUsersEmail(
      @RequestParam @Email String email,
      @RequestParam @NotBlank String username) {

    resetPasswordUserCase.sendNewPasswordToEmail(email, username);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/find-username")
  public ResponseEntity<Object> sendUsernameToUsersEmail(@RequestParam @Email String email) {

    String encryptedUsername = findEncryptedUsernameUseCase.findEncryptedUsername(email);
    return ResponseEntity.ok(encryptedUsername);
  }

  @GetMapping("/me")
  public UserProfileDto me(@AuthenticationPrincipal User user) {
    if(user == null) {
      throw new UserNotAuthenticatedException();
    }
    long userId = Long.parseLong(user.getUsername());
    UserProfileResult userProfileResult = findUserUseCase.findUserProfileById(userId);
    return UserProfileDto.of(userProfileResult);
  }

  @DeleteMapping("/user")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public LeaveUserDto leaveUser(@AuthenticationPrincipal User user) {
    long userId = Long.parseLong(user.getUsername());
    leaveUserUseCase.leave(userId);
    return new LeaveUserDto(userId);
  }

  @PatchMapping("/account/email")
  public void updateEmailByUser(@AuthenticationPrincipal User user,
                                @RequestBody UpdateEmailRequestDto updateEmailDto) {
    UpdateEmailCommand updateEmailCommand = new UpdateEmailCommand(
        Long.parseLong(user.getUsername()),
        updateEmailDto.verified(),
        updateEmailDto.email());

    updateAccountUseCase.updateUserEmail(updateEmailCommand);
  }

  @PatchMapping("/account/password")
  public void updatePasswordByUser(@AuthenticationPrincipal User user,
                                   @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
    UpdatePasswordCommand updatePasswordCommand = new UpdatePasswordCommand(
        Long.parseLong(user.getUsername()),
        updatePasswordRequestDto.oldPassword(),
        updatePasswordRequestDto.newPassword());

    updateAccountUseCase.updateUserPassword(updatePasswordCommand);
  }
}
