package io.perfume.api.user.adapter.in.http;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.user.adapter.in.http.dto.*;
import io.perfume.api.user.application.port.in.*;
import io.perfume.api.user.application.port.in.dto.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

  private final FindEncryptedUsernameUseCase findEncryptedUsernameUseCase;
  private final FindUserUseCase findUserUseCase;
  private final FindFileUseCase findFileUseCase;
  private final SendResetPasswordMailUseCase resetPasswordUserCase;
  private final UpdateAccountUseCase updateAccountUseCase;
  private final WithdrawUserUseCase withdrawUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final FindUserTasteUseCase findUserTasteUseCase;
  private final CreateUserTasteUseCase createUserTasteUseCase;

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public UserProfileDto me(@AuthenticationPrincipal User user) {
    final long userId = Long.parseLong(user.getUsername());
    final UserProfileResult userProfileResult = findUserUseCase.findUserProfileById(userId);

    return UserProfileDto.of(userProfileResult);
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/user")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public WithdrawUserResponseDto leaveUser(@AuthenticationPrincipal User user) {
    final LocalDateTime now = LocalDateTime.now();
    final long userId = Long.parseLong(user.getUsername());
    withdrawUserUseCase.withdraw(userId, now);

    return new WithdrawUserResponseDto(userId);
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/email")
  public void updateEmailByUser(
      @AuthenticationPrincipal User user, @RequestBody UpdateEmailRequestDto updateEmailDto) {
    UpdateEmailCommand updateEmailCommand =
        updateEmailDto.toCommand(Long.parseLong(user.getUsername()));

    updateAccountUseCase.updateUserEmail(updateEmailCommand);
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/password")
  public void updatePasswordByUser(
      @AuthenticationPrincipal User user,
      @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
    UpdatePasswordCommand updatePasswordCommand =
        updatePasswordRequestDto.toCommand(Long.parseLong(user.getUsername()));

    updateAccountUseCase.updateUserPassword(updatePasswordCommand);
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/profile")
  public void updateProfileByUser(
      @AuthenticationPrincipal User user,
      @RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
    UpdateProfileCommand updateProfileCommand =
        updateProfileRequestDto.toCommand(Long.parseLong(user.getUsername()));

    updateAccountUseCase.updateUserProfile(updateProfileCommand);
  }

  @PostMapping("signup")
  @ResponseStatus(HttpStatus.CREATED)
  public EmailSignUpResponseDto signUpUser(@RequestBody @Valid UserSignUpRequestDto dto) {
    final LocalDateTime now = LocalDateTime.now();
    final UserResult createdUser = createUserUseCase.createUser(dto.toCommand(now));

    return EmailSignUpResponseDto.from(createdUser);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("tastes")
  public List<GetUserTasteResponseDto> getTastes(@AuthenticationPrincipal final User user) {
    final long userId = Long.parseLong(user.getUsername());

    return findUserTasteUseCase.getUserTastes(userId).stream()
        .map(GetUserTasteResponseDto::from)
        .toList();
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("tastes")
  public ResponseEntity<Object> createTaste(
      @AuthenticationPrincipal final User uesr, @RequestBody final CreateUserTasteRequestDto dto) {
    final long userId = Long.parseLong(uesr.getUsername());
    createUserTasteUseCase.createUserTaste(userId, dto.noteId());

    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).build();
  }
}
