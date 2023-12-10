package io.perfume.api.user.adapter.out.persistence.social;

import io.perfume.api.user.adapter.out.persistence.user.UserEntity;
import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class SocialAccountMapper {

  private final UserMapper userMapper;

  public SocialAccountMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public SocialAccount toDomain(SocialAccountEntity socialAccount) {
    User user = userMapper.toUser(socialAccount.user);

    return new SocialAccount(
        socialAccount.id,
        socialAccount.identifier,
        socialAccount.socialProvider,
        user,
        socialAccount.getCreatedAt(),
        socialAccount.getUpdatedAt(),
        socialAccount.getDeletedAt());
  }

  public SocialAccountEntity toEntity(SocialAccount socialAccount) {
    UserEntity userEntity = userMapper.toUserJpaEntity(socialAccount.getUser());

    return new SocialAccountEntity(
        socialAccount.getId(),
        socialAccount.getIdentifier(),
        socialAccount.getSocialProvider(),
        userEntity,
        socialAccount.getCreatedAt(),
        socialAccount.getUpdatedAt(),
        socialAccount.getDeletedAt());
  }
}
