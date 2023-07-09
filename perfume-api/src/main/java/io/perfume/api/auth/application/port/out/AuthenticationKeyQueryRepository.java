package io.perfume.api.auth.application.port.out;

import io.perfume.api.auth.domain.AuthenticationKey;

import java.util.Optional;

public interface AuthenticationKeyQueryRepository {

  Optional<AuthenticationKey> findByKey(String key);
}
