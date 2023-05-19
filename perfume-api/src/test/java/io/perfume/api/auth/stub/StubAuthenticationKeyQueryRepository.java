package io.perfume.api.auth.stub;

import io.perfume.api.auth.adapter.out.persistence.persistence.AuthenticationKeyQueryRepository;
import io.perfume.api.auth.domain.AuthenticationKey;

import java.util.Optional;

public class StubAuthenticationKeyQueryRepository implements AuthenticationKeyQueryRepository {

    @Override
    public Optional<AuthenticationKey> findByKey(String key) {
        return Optional.empty();
    }
}
