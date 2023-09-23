package io.perfume.api.user.application.service;

import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.SocialAccountQueryRepository;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindUserService implements FindUserUseCase {

  private final UserQueryRepository userQueryRepository;

  private final SocialAccountQueryRepository oauthQueryRepository;

  public FindUserService(UserQueryRepository userQueryRepository,
                         SocialAccountQueryRepository oauthQueryRepository) {
    this.userQueryRepository = userQueryRepository;
    this.oauthQueryRepository = oauthQueryRepository;
  }

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
    return userQueryRepository.findUsersByIds(userIds).stream()
        .map(this::toDto)
        .toList();
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
  }

  private UserResult toDto(SocialAccount socialAccount) {
    return toDto(socialAccount.getUser());
  }
}
