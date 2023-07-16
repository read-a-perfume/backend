package io.perfume.api.user.application.port.out;

import io.perfume.api.user.domain.SocialAccount;
import java.util.Optional;
import javax.swing.text.html.Option;

public interface OAuthQueryRepository {

  Optional<SocialAccount> findByIdentifier(String identifier);
}
