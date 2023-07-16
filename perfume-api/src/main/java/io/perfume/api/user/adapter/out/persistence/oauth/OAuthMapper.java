package io.perfume.api.user.adapter.out.persistence.oauth;

import io.perfume.api.user.adapter.out.persistence.user.UserJpaEntity;
import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.domain.SocialAccount;
import io.perfume.api.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class OAuthMapper {

  private final UserMapper userMapper;

  public OAuthMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public SocialAccount toDomain(OAuthJpaEntity oauthJpaEntity) {
    User user = userMapper.toUser(oauthJpaEntity.user);

    return new SocialAccount(
        oauthJpaEntity.id,
        oauthJpaEntity.identifier,
        oauthJpaEntity.email,
        oauthJpaEntity.socialProvider,
        user,
        oauthJpaEntity.getCreatedAt(),
        oauthJpaEntity.getUpdatedAt(),
        oauthJpaEntity.getDeletedAt()
    );
  }

  public OAuthJpaEntity toEntity(SocialAccount socialAccount) {
    UserJpaEntity userJpaEntity = userMapper.toUserJpaEntity(socialAccount.getUser());
    return new OAuthJpaEntity(
        socialAccount.getId(),
        socialAccount.getIdentifier(),
        socialAccount.getEmail(),
        socialAccount.getSocialProvider(),
        userJpaEntity,
        socialAccount.getCreatedAt(),
        socialAccount.getUpdatedAt(),
        socialAccount.getDeletedAt()
    );
  }
}
