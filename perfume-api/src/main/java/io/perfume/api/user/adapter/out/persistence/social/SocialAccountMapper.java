package io.perfume.api.user.adapter.out.persistence.social;

import io.perfume.api.user.adapter.out.persistence.user.UserJpaEntity;
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

  public SocialAccount toDomain(SocialAccountJpaEntity oauthJpaEntity) {
    User user = userMapper.toUser(oauthJpaEntity.user);

    return new SocialAccount(
        oauthJpaEntity.id,
        oauthJpaEntity.identifier,
        oauthJpaEntity.socialProvider,
        user,
        oauthJpaEntity.getCreatedAt(),
        oauthJpaEntity.getUpdatedAt(),
        oauthJpaEntity.getDeletedAt()
    );
  }

  public SocialAccountJpaEntity toEntity(SocialAccount socialAccount) {
    UserJpaEntity userJpaEntity = userMapper.toUserJpaEntity(socialAccount.getUser());

    return new SocialAccountJpaEntity(
        socialAccount.getId(),
        socialAccount.getIdentifier(),
        socialAccount.getSocialProvider(),
        userJpaEntity,
        socialAccount.getCreatedAt(),
        socialAccount.getUpdatedAt(),
        socialAccount.getDeletedAt()
    );
  }
}
