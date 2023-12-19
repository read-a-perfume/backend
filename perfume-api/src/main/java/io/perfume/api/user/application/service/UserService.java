package io.perfume.api.user.application.service;

import io.perfume.api.user.application.exception.NotFoundUserException;
import io.perfume.api.user.application.exception.UserConflictException;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.SocialAccountQueryRepository;
import io.perfume.api.user.application.port.out.SocialAccountRepository;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.application.service.dto.*;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserQueryRepository userQueryRepository;
  private final UserRepository userRepository;
  private final SocialAccountRepository socialAccountRepository;
  private final SocialAccountQueryRepository socialAccountQueryRepository;
  private final PasswordEncoder passwordEncoder;

  public long withdrawUser(final long userId, final LocalDateTime deletedAt) {
    final User user =
        userQueryRepository.findUserById(userId).orElseThrow(NotFoundUserException::new);

    user.markDelete(deletedAt);
    userRepository.save(user);

    return user.getId();
  }

  public UserResult updateProfile(final long userId, final UpdateUserProfileCommand command) {
    final User user =
        userQueryRepository.findUserById(userId).orElseThrow(NotFoundUserException::new);

    user.updateProfile(command);

    return UserResult.of(userRepository.save(user));
  }

  public UserResult updatePassword(final long userId, final UpdateUserPasswordCommand command) {
    final User user =
        userQueryRepository.findUserById(userId).orElseThrow(NotFoundUserException::new);

    if (!user.verifyPassword(command.currentPassword(), passwordEncoder)) {
      throw new IllegalArgumentException("Password is not correct");
    }
    user.updatePassword(command.newPassword(), passwordEncoder);

    return UserResult.of(userRepository.save(user));
  }

  public UserResult createUser(final CreateUserCommand command) {
    checkUserConflict(command.email(), command.username());
    final User user = registerUser(command);

    return UserResult.of(user);
  }

  private User registerUser(CreateUserCommand command) {
    final User user =
        User.generalUserJoin(
            command.username(),
            command.email(),
            passwordEncoder.encode(command.password()),
            command.marketingConsent(),
            command.promotionConsent());
    userRepository.save(user);

    return user;
  }

  public UserResult createSocialUser(final CreateSocialUserCommand command) {
    checkUserConflict(command.email(), command.username());
    final User user = registerSocialUser(command);
    connectGoogleSocialAccount(command, user);

    return UserResult.of(user);
  }

  private void connectGoogleSocialAccount(CreateSocialUserCommand command, User user) {
    final SocialAccount socialAccount =
        SocialAccount.createGoogleSocialAccount(
            command.identifier(), command.registrationDateTime());
    socialAccount.connect(user);
    socialAccountRepository.save(socialAccount);
  }

  private User registerSocialUser(CreateSocialUserCommand command) {
    final User user =
        User.createSocialUser(
            command.username(),
            command.email(),
            passwordEncoder.encode(command.password()),
            command.registrationDateTime());

    return userRepository.save(user);
  }

  private void checkUserConflict(String email, String username) {
    userQueryRepository
        .findOneByEmailAndUsername(email, username)
        .ifPresent(
            user -> {
              throw new UserConflictException(username, email);
            });
  }

  @Transactional(readOnly = true)
  public Optional<UserResult> findOneByEmail(final String email) {
    return userQueryRepository.findOneByEmail(email).map(UserResult::of);
  }

  @Transactional(readOnly = true)
  public Optional<UserResult> findOneBySocialId(final String socialId) {
    return socialAccountQueryRepository
        .findOneBySocialId(socialId)
        .map(SocialAccount::getUser)
        .map(UserResult::of);
  }

  @Transactional(readOnly = true)
  public List<UserResult> findUsersByIds(final List<Long> ids) {
    return userQueryRepository.findUsersByIds(ids).stream().map(UserResult::of).toList();
  }

  @Transactional(readOnly = true)
  public Optional<UserResult> findUserById(final long id) {
    return userQueryRepository.findUserById(id).map(UserResult::of);
  }

  @Transactional(readOnly = true)
  public Optional<UserResult> findByUsername(final String username) {
    return userQueryRepository.findByUsername(username).map(UserResult::of);
  }
}
