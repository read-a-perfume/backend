package io.perfume.api.user.application.facade;

import io.perfume.api.file.application.port.in.dto.FileResult;
import io.perfume.api.file.application.service.FileService;
import io.perfume.api.note.application.port.in.CreateCategoryUseCase;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import io.perfume.api.note.application.port.in.dto.AddUserTasteCommand;
import io.perfume.api.note.application.port.in.dto.CategoryResult;
import io.perfume.api.user.application.exception.NotFoundUserException;
import io.perfume.api.user.application.port.in.*;
import io.perfume.api.user.application.port.in.dto.*;
import io.perfume.api.user.application.service.UserService;
import io.perfume.api.user.application.service.dto.UpdateUserProfileCommand;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mailer.MailSender;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserFacadeService
    implements CreateUserTasteUseCase,
        CreateUserUseCase,
        FindEncryptedUsernameUseCase,
        FindUserTasteUseCase,
        FindUserUseCase,
        WithdrawUserUseCase,
        SendResetPasswordMailUseCase,
        SetUserProfileUseCase,
        UpdateAccountUseCase {

  private final UserService userService;
  private final FileService fileService;
  private final MailSender mailSender;

  // TODO: use case 참조 제거
  private final FindCategoryUseCase findCategoryUseCase;
  private final CreateCategoryUseCase createCategoryUseCase;

  @Override
  public void sendPasswordResetEmail(final String email) {
    final UserResult user =
        userService.findOneByEmail(email).orElseThrow(NotFoundUserException::new);
    mailSender.send(user.email(), "", "");
  }

  @Override
  @Transactional
  public UserTasteResult createUserTaste(final long userId, final long categoryId) {
    final CategoryResult category =
        createCategoryUseCase.create(
            new AddUserTasteCommand(userId, categoryId), LocalDateTime.now());

    return UserTasteResult.from(category);
  }

  @Override
  public List<UserTasteResult> getUserTastes(final long userId) {
    return findCategoryUseCase.findTasteByUserId(userId).stream()
        .map(UserTasteResult::from)
        .toList();
  }

  @Override
  @Transactional
  public UserResult createUser(SignUpGeneralUserCommand command) {
    // TODO: email 인증 발송
    return userService.createUser(command.toCreateUserCommand());
  }

  @Override
  @Transactional
  public UserResult createSocialUser(SignUpSocialUserCommand command) {
    return userService.createSocialUser(command.toCreateSocialUserCommand());
  }

  @Override
  public String getCensoredUsername(String email) {
    final UserResult user =
        userService.findOneByEmail(email).orElseThrow(NotFoundUserException::new);
    log.info("Request to find username userId={}", user.id());
    return censorUsername(user);
  }

  @NotNull
  private static String censorUsername(UserResult user) {
    final String username = user.username();
    final int CENSOR_LENGTH = 3;
    return username.substring(0, CENSOR_LENGTH) + "****";
  }

  @Override
  public Optional<UserResult> findOneByEmail(String email) {
    return userService.findOneByEmail(email);
  }

  @Override
  public Optional<UserResult> findOneBySocialId(String socialId) {
    return userService.findOneBySocialId(socialId);
  }

  @Override
  public List<UserResult> findUsersByIds(List<Long> userIds) {
    return userService.findUsersByIds(userIds);
  }

  @Override
  public Optional<UserResult> findUserById(long userId) {
    return userService.findUserById(userId);
  }

  @Override
  public UserProfileResult findUserProfileById(long userId) {
    final UserResult user =
        userService.findUserById(userId).orElseThrow(NotFoundUserException::new);
    final Optional<FileResult> file = fileService.findById(user.thumbnailId());

    return UserProfileResult.from(user, file);
  }

  @Override
  @Transactional
  public void withdraw(final long userId, final LocalDateTime withdrawnAt) {
    userService.withdrawUser(userId, withdrawnAt);
  }

  @Override
  @Transactional
  public long updateUserThumbnail(final long userId, final byte[] image, final LocalDateTime now) {
    final FileResult file = fileService.uploadFile(userId, image, now);
    final UserResult user =
        userService.updateProfile(
            userId, UpdateUserProfileCommand.createUpdateThumbnail(file.id()));

    return user.id();
  }

  @Override
  @Transactional
  public long updateUserEmail(UpdateEmailCommand updateEmailCommand) {
    final UserResult user =
        userService.updateProfile(
            updateEmailCommand.userId(), updateEmailCommand.toUpdateUserEmailCommand());

    return user.id();
  }

  @Override
  @Transactional
  public long updateUserPassword(final UpdatePasswordCommand command) {
    final UserResult user =
        userService.updatePassword(command.userId(), command.toUpdateUserPasswordCommand());
    log.info("Update password userId={}", user.id());

    return user.id();
  }

  @Override
  public long updateUserProfile(final UpdateProfileCommand command) {
    final UserResult user =
        userService.updateProfile(command.userId(), command.toUpdateUserProfileCommand());

    return user.id();
  }
}
