package io.perfume.api.user.adapter.out.persistence.oauth;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.user.application.port.out.OAuthRepository;
import io.perfume.api.user.domain.SocialAccount;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class OAuthPersistenceAdapter implements OAuthRepository {

  private final OAuthJpaRepository oauthJpaRepository;
  private final OAuthMapper oauthMapper;

  @Override
  public Optional<SocialAccount> save(SocialAccount socialAccount) {
    OAuthJpaEntity oauthJpaEntity = oauthMapper.toEntity(socialAccount);

    oauthJpaRepository.save(oauthJpaEntity);

    return Optional.of(oauthMapper.toDomain(oauthJpaEntity));
  }
}
