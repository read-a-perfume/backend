package io.perfume.api.auth.adapter.out.persistence.persistence;

import io.perfume.api.auth.domain.AuthenticationKey;

import java.util.Optional;

public interface AuthenticationKeyQueryRepository {

    Optional<AuthenticationKey> findByKey(String key);
}
