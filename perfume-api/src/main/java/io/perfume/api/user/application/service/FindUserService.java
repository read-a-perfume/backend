package io.perfume.api.user.application.service;

import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import io.perfume.api.user.application.exception.UserNotFoundException;
import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserProfileResult;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.SocialAccountQueryRepository;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

  private final UserQueryRepository userQueryRepository;

  private final SocialAccountQueryRepository oauthQueryRepository;

  private final FindFileUseCase findFileUseCase;

  @Override
  @Transactional(readOnly = true)
  public Optional<UserResult> findOneByEmail(String email) {
    return userQueryRepository.findOneByEmail(email).map(this::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserResult> findOneBySocialId(String socialId) {
    return oauthQueryRepository.findOneBySocialId(socialId).map(this::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserResult> findUsersByIds(List<Long> userIds) {
    return userQueryRepository.findUsersByIds(userIds).stream().map(this::toDto).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserResult> findUserById(long userId) {
    return userQueryRepository.findUserById(userId).map(this::toDto);
  }

  @Override
  public UserProfileResult findUserProfileById(long userId) {
    User user =
        userQueryRepository
            .findUserById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    Optional<File> fileById = findFileUseCase.findFileById(user.getThumbnailId());
    String thumbnail = "";
    if (fileById.isPresent()) {
      thumbnail = fileById.get().getUrl();
    }
    return new UserProfileResult(user.getId(), user.getUsername(), thumbnail);
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
  }

  private UserResult toDto(SocialAccount socialAccount) {
    return toDto(socialAccount.getUser());
  }
}
