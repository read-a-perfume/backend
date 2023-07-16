package io.perfume.api.user.stub;

import io.lettuce.core.XReadArgs;
import io.perfume.api.user.application.port.out.OAuthRepository;
import io.perfume.api.user.domain.SocialAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubOAuthRepository implements OAuthRepository {

  @Override
  public Optional<SocialAccount> save(SocialAccount socialAccount) {
    return Optional.of(socialAccount);
  }
}
