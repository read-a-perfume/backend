package io.perfume.api.user.application.service;

import io.perfume.api.user.application.port.in.FindUserUseCase;
import io.perfume.api.user.application.port.in.dto.UserResult;
import io.perfume.api.user.application.port.out.SocialAccountQueryRepository;
import io.perfume.api.user.application.port.out.UserQueryRepository;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
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
  @Transactional
  public Optional<UserResult> findOneByEmail(String email) {
    return userQueryRepository.findOneByEmail(email).map(this::toDto);
  }

  @Override
  @Transactional
  public Optional<UserResult> findOneBySocialId(String socialId) {
    return oauthQueryRepository.findOneBySocialId(socialId).map(this::toDto);
  }

  private UserResult toDto(User user) {
    return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
  }

  private UserResult toDto(SocialAccount socialAccount) {
    return toDto(socialAccount.getUser());
  }
}
