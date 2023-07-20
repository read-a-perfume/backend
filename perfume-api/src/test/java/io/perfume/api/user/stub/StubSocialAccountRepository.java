package io.perfume.api.user.stub;

import io.perfume.api.user.application.port.out.SocialAccountRepository;
import io.perfume.api.user.domain.SocialAccount;
import java.util.Optional;

public class StubSocialAccountRepository implements SocialAccountRepository {

  @Override
  public Optional<SocialAccount> save(SocialAccount socialAccount) {
    return Optional.of(socialAccount);
  }
}
