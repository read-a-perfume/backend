package io.perfume.api.user.adapter.in.http;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.review.application.in.review.GetUserReviewsUseCase;
import io.perfume.api.user.adapter.in.http.dto.*;
import io.perfume.api.user.adapter.in.http.exception.UserNotAuthenticatedException;
import io.perfume.api.user.application.port.in.*;
import io.perfume.api.user.application.port.in.dto.MyInfoResult;
import io.perfume.api.user.application.port.in.dto.UpdateEmailCommand;
import io.perfume.api.user.application.port.in.dto.UpdatePasswordCommand;
import io.perfume.api.user.application.port.in.dto.UpdateProfileCommand;
import io.perfume.api.user.application.port.in.dto.UserProfileResult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserSupportController {

  private final FindEncryptedUsernameUseCase findEncryptedUsernameUseCase;
  private final FindUserUseCase findUserUseCase;
  private final FindFileUseCase findFileUseCase;
  private final SendResetPasswordMailUseCase resetPasswordUserCase;
  private final UpdateAccountUseCase updateAccountUseCase;
  private final UpdateProfilePicUseCase updateProfilePicUseCase;
  private final LeaveUserUseCase leaveUserUseCase;
  private final GetUserReviewsUseCase getUserReviewsUseCase;

  /** TODO OAUTH 가입자의 경우 uesrname도 같이 넣는 흐름이 이상하다. */
  @GetMapping("/email/reset-password")
  public ResponseEntity<Object> sendPasswordToUsersEmail(
      @RequestParam @Email String email, @RequestParam @NotBlank String username) {

    resetPasswordUserCase.sendNewPasswordToEmail(email, username);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/find-username")
  public ResponseEntity<Object> sendUsernameToUsersEmail(@RequestParam @Email String email) {

    String encryptedUsername = findEncryptedUsernameUseCase.findEncryptedUsername(email);
    return ResponseEntity.ok(encryptedUsername);
  }

  @GetMapping("/me")
  public MyInfoDto me(@AuthenticationPrincipal User user) {
    checkAuthenticatedUser(user);

    long userId = Long.parseLong(user.getUsername());
    MyInfoResult myInfoResult = findUserUseCase.findMyInfoById(userId);
    return MyInfoDto.of(myInfoResult);
  }

  @GetMapping("/user/{id}")
  public UserProfileDto getUser(@PathVariable Long id) {
    UserProfileResult userProfileResult = findUserUseCase.findUserProfileById(id);
    return UserProfileDto.of(userProfileResult);
  }

  @DeleteMapping("/user")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public LeaveUserDto leaveUser(@AuthenticationPrincipal User user) {
    checkAuthenticatedUser(user);

    long userId = Long.parseLong(user.getUsername());
    leaveUserUseCase.leave(userId);
    return new LeaveUserDto(userId);
  }

  @PatchMapping("/account/email")
  public void updateEmailByUser(
      @AuthenticationPrincipal User user, @RequestBody UpdateEmailRequestDto updateEmailDto) {
    checkAuthenticatedUser(user);

    UpdateEmailCommand updateEmailCommand =
        updateEmailDto.toCommand(Long.parseLong(user.getUsername()));

    updateAccountUseCase.updateUserEmail(updateEmailCommand);
  }

  @PatchMapping("/account/password")
  public void updatePasswordByUser(
      @AuthenticationPrincipal User user,
      @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
    checkAuthenticatedUser(user);

    UpdatePasswordCommand updatePasswordCommand =
        updatePasswordRequestDto.toCommand(Long.parseLong(user.getUsername()));

    updateAccountUseCase.updateUserPassword(updatePasswordCommand);
  }

  @PatchMapping("/user/profile")
  public void updateProfileByUser(
      @AuthenticationPrincipal User user,
      @RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
    checkAuthenticatedUser(user);

    UpdateProfileCommand updateProfileCommand =
        updateProfileRequestDto.toCommand(Long.parseLong(user.getUsername()));

    updateAccountUseCase.updateUserProfile(updateProfileCommand);
  }

  @PatchMapping("/user/profile-pic")
  @PreAuthorize("isAuthenticated()")
  public void updateProfilePic(@AuthenticationPrincipal User user, final MultipartFile file) {
    var now = LocalDateTime.now();
    checkAuthenticatedUser(user);
    long userId = parseUserId(user);
    updateProfilePicUseCase.update(userId, file, now);
  }

  @GetMapping("/users/{id}/reviews")
  public ResponseEntity<Object> getUserReviews(
      @PathVariable final long id, final GetUserReviewsRequestDto dto) {
    return ResponseEntity.ok(
        getUserReviewsUseCase.getUserReviews(id, dto.getPage(), dto.getSize()));
  }

  private long parseUserId(User user) {
    return Long.parseLong(user.getUsername());
  }

  private void checkAuthenticatedUser(User user) {
    if (user == null || user.getUsername() == null) {
      throw new UserNotAuthenticatedException();
    }
  }
}
