package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.SocialAccount;
import java.util.Optional;

public interface OAuthRepository {

  Optional<SocialAccount> save(SocialAccount socialAccount);
}
