package io.perfume.api.user.adapter.out.persistence.social;

import io.perfume.api.user.application.port.out.SocialAccountRepository;
import io.perfume.api.user.domain.SocialAccount;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class SocialAccountPersistenceAdapter implements SocialAccountRepository {

  private final SocialAccountJpaRepository oauthJpaRepository;
  private final SocialAccountMapper oauthMapper;

  @Override
  public Optional<SocialAccount> save(SocialAccount socialAccount) {
    SocialAccountEntity entity = oauthMapper.toEntity(socialAccount);

    oauthJpaRepository.save(entity);

    return Optional.of(oauthMapper.toDomain(entity));
  }
}
